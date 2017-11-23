
package com.github.quartzweb;

/**
 * @author leisure
 */
public class VERSION {
    public final static int MajorVersion    = 1;
    public final static int MinorVersion    = 3;
    public final static int RevisionVersion = 0;

    public static String getVersionNumber() {
        return VERSION.MajorVersion + "." + VERSION.MinorVersion + "." + VERSION.RevisionVersion;
    }
}
