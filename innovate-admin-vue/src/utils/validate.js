/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/**
 * 学号
 * @param {*} s
 */
export function isStuNum (s) {
  return /^(\d{12})$/.test(s)
}

/**
 * QQ
 * @param {*} s
 */
export function isQQ (s) {
  return /^(\d{5,11})$/.test(s)
}

/**
 * 浮点数字
 * @param {*} s
 */
export function isFloatNumber (s) {
  return /^([0-9]\d*|0)(\.\d{1,7})$/.test(s)
}
/**
 * 身份证号码
 * @param {*} s
 */
// export function isIdNo (s) {
//   return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(s)
// }

/**
 * 整数数字
 * @param {*} s
 */
export function isNumber (s) {
  return /^(\d+)$/.test(s)
}
