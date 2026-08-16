
fun validateCitizenId(id: String): Boolean {
    // เงื่อนไข: ความยาวต้องเท่ากับ 13 พอดี (ครอบคลุมกรณีว่างเปล่าด้วย)
    if (id.length != 13) return false

    // เงื่อนไข: ต้องเป็นเลขอารบิก 0-9 เท่านั้น
    // หมายเหตุ: ห้ามใช้ isDigit() เพราะคืน true กับเลขไทย ๐-๙ ด้วย (Unicode Nd)
    if (!id.all { it in '0'..'9' }) return false

    val digits = id.map { it.toString().toInt() }

    // คำนวณผลรวมถ่วงน้ำหนักจาก 12 หลักแรก
    var sum = 0
    for (i in 0 until 12) {
        sum += digits[i] * (13 - i)
    }

    val checkDigit = (11 - sum % 11) % 10

    return checkDigit == digits[12]
}