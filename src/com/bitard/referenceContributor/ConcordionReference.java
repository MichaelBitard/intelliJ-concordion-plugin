package com.bitard.referenceContributor;

import com.bitard.ConcordionIcon;
import com.bitard.ConcordionUtil;
import com.bitard.psi.ConcordionMethodInHtml;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConcordionReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public ConcordionReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<ConcordionMethodInHtml> properties = ConcordionUtil.findProperties(project, key);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (XmlAttribute property : properties) {
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<ConcordionMethodInHtml> properties = ConcordionUtil.findProperties(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final XmlAttribute property : properties) {
            variants.add(LookupElementBuilder.create(property).
                    withIcon(ConcordionIcon.FILE).
                    withTypeText(property.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}
