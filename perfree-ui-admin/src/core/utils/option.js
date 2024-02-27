import store from "@/core/store";

export const OPTION_KEY = {
    DEFAULT_ADMIN_FRAME: 'DEFAULT_ADMIN_FRAME',
    LOGIN_CAPTCHA_ENABLE: 'LOGIN_CAPTCHA_ENABLE'
}

/**
 * 根据key获取option
 * @param key
 * @returns {*|null}
 */
export function getOptionByKey(key) {
    return store.getters.options[key] || null;
}