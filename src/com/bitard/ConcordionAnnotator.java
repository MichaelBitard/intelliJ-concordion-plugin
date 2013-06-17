package com.bitard;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConcordionAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;
            String value = method.getName();
            if (ConcordionUtil.isMethodBelongToAConcordionClass(method.getContainingClass())) {
                Project project = element.getProject();
                List<XmlAttribute> properties = ConcordionUtil.findProperties(project, value);

                if (properties.size() == 0) {
                    TextRange range = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
                    holder.createErrorAnnotation(range, "Unused method in concordion files");
                }
            }
        }
    }
}
