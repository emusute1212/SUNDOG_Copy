package io.sundog.copy.manager;

import org.apache.commons.lang3.SystemUtils;

/**
 * Created by Yosuke on 2017/02/14.
 */
public class OSChecker {
    /**OSの種別*/
    public enum OSName {
        /**AIX*/
        AIX,
        /**HP_UX*/
        HP_UX,
        /**IRIX*/
        IRIX,
        /**LINUX*/
        LINUX,
        /**MAC*/
        MAC,
        /**MAC_OSX*/
        MAC_OSX,
        /**OS2*/
        OS2,
        /**SOLARIS*/
        SOLARIS,
        /**SUN_OS*/
        SUN_OS,
        /**WINDOWS*/
        WINDOWS,
        /**不明*/
        UNKNOWN
    }


    /**
     * OS名を取得する。<
     * @return OS名
     */
    public static final OSName getOsName() {
        if ( SystemUtils.IS_OS_AIX ) {
            return OSName.AIX;
        } else if ( SystemUtils.IS_OS_HP_UX ) {
            return OSName.HP_UX;
        } else if ( SystemUtils.IS_OS_IRIX ) {
            return OSName.IRIX;
        } else if ( SystemUtils.IS_OS_LINUX ) {
            return OSName.LINUX;
        } else if ( SystemUtils.IS_OS_MAC ) {
            return OSName.MAC;
        } else if ( SystemUtils.IS_OS_MAC_OSX ) {
            return OSName.MAC_OSX;
        } else if ( SystemUtils.IS_OS_OS2 ) {
            return OSName.OS2;
        } else if ( SystemUtils.IS_OS_SOLARIS ) {
            return OSName.SOLARIS;
        } else if ( SystemUtils.IS_OS_SUN_OS ) {
            return OSName.SUN_OS;
        } else if ( SystemUtils.IS_OS_WINDOWS ) {
            return OSName.WINDOWS;
        }
        return OSName.UNKNOWN;
    }
}
