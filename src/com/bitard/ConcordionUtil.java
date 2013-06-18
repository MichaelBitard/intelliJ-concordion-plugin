package com.bitard;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.impl.source.html.HtmlFileImpl;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.util.indexing.FileBasedIndex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConcordionUtil {
    public static List<XmlAttribute> findProperties(Project project, String key) {
        List<XmlAttribute> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, HtmlFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            HtmlFileImpl htmlFile = (HtmlFileImpl) PsiManager.getInstance(project).findFile(virtualFile);
            if (htmlFile != null) {
                Collection<XmlAttribute> properties = PsiTreeUtil.findChildrenOfType(htmlFile, XmlAttribute.class);
                if (properties != null) {
                    for (XmlAttribute property : properties) {
                        String value = property.getValue();
                        if (value != null && value.contains(key)) {
                            if (result == null) {
                                result = new ArrayList<XmlAttribute>();
                            }
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<XmlAttribute>emptyList();
    }

    public static boolean isMethodBelongToAConcordionClass(PsiClass containingClass) {
        return ClassHasAConcordionRunnerAnnotation(containingClass) || isThereAConcordionAnnotationInParent(containingClass);
    }

    private static boolean ClassHasAConcordionRunnerAnnotation(PsiClass containingClass) {
        if (containingClass != null) {
            PsiModifierList modifierList = containingClass.getModifierList();
            if (modifierList != null) {
                PsiAnnotation[] annotations = modifierList.getAnnotations();
                for (PsiAnnotation annotation : annotations) {
                    String qualifiedName = annotation.getQualifiedName();
                    if (qualifiedName != null && qualifiedName.contains("RunWith")) {
                        PsiAnnotationMemberValue value = annotations[0].getParameterList().getAttributes()[0].getValue();
                        if (value != null && value.getText().contains("ConcordionRunner")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isThereAConcordionAnnotationInParent(PsiClass containingClass) {
        final PsiClass[] superClasses = containingClass.getSupers();
        for (PsiClass superClass : superClasses) {
            if (ClassHasAConcordionRunnerAnnotation(superClass)) {
                return true;
            }
            if (superClass.getSupers().length > 0) {
                return isThereAConcordionAnnotationInParent(superClass);
            }
        }
        return false;
    }
}
