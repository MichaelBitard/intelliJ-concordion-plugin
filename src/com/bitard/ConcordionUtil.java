package com.bitard;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
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
                        if (property.getValue().contains(key)) {
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
}
