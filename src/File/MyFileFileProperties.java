/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 *
 * @author Karayel
 */
public class MyFileFileProperties {
    public static FileTime getProperties(char c, BasicFileAttributes attr) {
        if (c == 'c') {
            return attr.creationTime();
        } else if (c == 'a') {
            return attr.lastAccessTime();
        } else if (c == 'm') {
            return attr.lastModifiedTime();
        }else{
            return null;
        }
    }
}
