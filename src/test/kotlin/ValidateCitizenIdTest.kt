import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

/*
 * สูตร checksum เลขบัตรประชาชนไทย:
 * sum = ผลรวมของ (หลักที่ i) * (13 - i) สำหรับ 12 หลักแรก
 * check = (11 - sum % 11) % 10
 */
class ValidateCitizenIdTest {

    // ---------- STEP 1: เทสต์ 3 ตัวแรก เขียนโชว์บนจอ ----------

    @Test
    fun `valid 13 digit id returns true`() {
        // Arrange
        val id = "1101700185206" // เลขสมมติ checksum ถูกต้อง
        // Act
        val result = validateCitizenId(id)
        // Assert
        assertTrue(actual = result)
    }

    @Test
    fun `id with wrong length returns false`() {
        assertFalse(actual = validateCitizenId("12345"))              // สั้นไป
        assertFalse(actual = validateCitizenId("11017001852066"))     // ยาวไป (14 หลัก)
        assertFalse(actual = validateCitizenId(""))                   // ว่างเปล่า
    }

    @Test
    fun `id containing non digit characters returns false`() {
        assertFalse(actual = validateCitizenId("1101700A85206"))
        assertFalse(actual = validateCitizenId("abcdefghijklm"))
        assertFalse(actual = validateCitizenId("1101700 85206"))      // มีช่องว่าง
    }

    // ---------- STEP 2: เคสภาษาไทย ----------

    @Test
    fun `id with thai numerals returns false`() {
        // กับดัก: Char.isDigit() คืน true กับเลขไทย (Unicode category Nd)
        // ต้องใช้ it in '0'..'9' เท่านั้นถึงจะดักได้
        assertFalse(actual = validateCitizenId("๑๑๐๑๗๐๐๑๘๕๒๐๖"))     // เลขไทยล้วน 13 ตัว
        assertFalse(actual = validateCitizenId("110170018520๖"))      // ปนเลขไทยตัวเดียว
        assertFalse(actual = validateCitizenId("๑101700185206"))      // เลขไทยตัวแรก
    }

    @Test
    fun `id with thai letters returns false`() {
        assertFalse(actual = validateCitizenId("กขคงจฉชซฌญฎฏฐ"))       // อักษรไทยล้วน 13 ตัว
        assertFalse(actual = validateCitizenId("110170018520ก"))      // ปนอักษรไทย
        assertFalse(actual = validateCitizenId("เลขบัตรประชาชน"))      // ข้อความไทย
    }

    @Test
    fun `id with thai combining characters returns false`() {
        // สระ/วรรณยุกต์ไทยนับเป็น 1 char แยกต่างหาก
        // "ก้" ดูเหมือน 1 ตัว แต่ String.length นับได้ 2
        assertFalse(actual = validateCitizenId("110170018520ก้"))     // length = 14 จริง ๆ
        assertFalse(actual = validateCitizenId("11017001852่0"))      // ไม้เอกลอยเดี่ยว
    }

    // ---------- STEP 3: ลบคอมเมนต์บล็อกนี้ระหว่างเดโม -> เทสต์จะแดง ----------

    @Test
    fun `id with wrong checksum returns false`() {
        // หลักที่ 13 ต้องเป็น check digit ที่คำนวณจาก 12 หลักแรก
        // 110170018520 → check digit ที่ถูกต้องคือ 6
        assertFalse(validateCitizenId("1101700185207")) // หลักสุดท้ายผิด
        assertFalse(validateCitizenId("1234567890129")) // ที่ถูกคือ ...1

        // ใบที่ checksum ถูกต้อง ต้องยังผ่านอยู่
        assertTrue(validateCitizenId("3509900547250"))
        assertTrue(validateCitizenId("1234567890121"))
    }
}