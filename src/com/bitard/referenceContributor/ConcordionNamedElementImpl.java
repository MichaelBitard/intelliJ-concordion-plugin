package com.bitard.referenceContributor;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class ConcordionNamedElementImpl extends ASTWrapperPsiElement implements ConcordionNamedElement {
    public ConcordionNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
