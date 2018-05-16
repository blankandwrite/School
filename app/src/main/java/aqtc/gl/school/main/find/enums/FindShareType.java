package aqtc.gl.school.main.find.enums;

/**
 * @author gl
 * @date 2018/5/16
 * @desc 发现动态枚举,区分动态类型
 */
public enum FindShareType {
    TEXT_ONLY, JUMP, LINK_URL, NONE;

    public boolean isTextOnly() {
        return this == TEXT_ONLY;
    }

    public boolean isJump() {
        return this == JUMP;
    }

    public boolean isLinkUrl() {
        return this == LINK_URL;
    }

    public boolean isNone() {
        return this == NONE;
    }
}
