import org.example.Product
import org.example.calculateTotalElectronicsPriceOver500
import org.example.celsiusToFahrenheit
import org.example.countElectronicsProductsOver500
import org.example.kilometersToMiles
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkshopTest {

    // --- Tests for Workshop #1: Unit Converter ---

    // celsius input: 20.0
    // expected output: 68.0
    @Test
    fun `test celsiusToFahrenheit with positive value`() {
        // Arrange: ตั้งค่า input และผลลัพธ์ที่คาดหวัง
        val celsiusInput = 20.0
        val expectedFahrenheit = 68.0

        // Act: เรียกใช้ฟังก์ชันที่ต้องการทดสอบ
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)

        // Assert: ตรวจสอบว่าผลลัพธ์ที่ได้ตรงกับที่คาดหวัง
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "20°C should be 68°F")
    }

    // celsius input: 0.0
    // expected output: 32.0
    @Test
    fun `test celsiusToFahrenheit with zero`() {

        val celsiusInput = 0.0
        val expectedFahrenheit = 32.0

        val actualFahrenheit = celsiusToFahrenheit(celsius = celsiusInput)

        assertEquals(
            expected = expectedFahrenheit,
            actual = actualFahrenheit,
            absoluteTolerance = 0.001
        )
    }

    // celsius input: -10.0
// expected output: 14.0
    @Test
    fun `test celsiusToFahrenheit with negative value`() {
        val result = celsiusToFahrenheit(-10.0)
        assertEquals(14.0, result, 0.001)
    }

    // test for kilometersToMiles function
// kilometers input: 1.0
// expected output: 0.621371
    @Test
    fun `test kilometersToMiles with one kilometer`() {
        val result = kilometersToMiles(1.0)
        assertEquals(0.621371, result, 0.000001)
    }

// --- Tests for Workshop #1: Unit Converter End ---

// --- Tests for Workshop #2: Data Analysis Pipeline ---
// data class Product(val name: String, val price: Double, val category: String)
// และฟังก์ชัน calculateTotalElectronicsPriceOver500(products: List<Product>): Double

    @Test
    fun `test calculateTotalElectronicsPriceOver500 with mixed products`() {
        val products = listOf(
            Product("Laptop", 25000.0, "Electronics"),
            Product("Mouse", 300.0, "Electronics"),      // ไม่เข้าเงื่อนไข ราคา <= 500
            Product("Phone", 15000.0, "Electronics"),
            Product("Shirt", 800.0, "Clothing"),          // ไม่เข้าเงื่อนไข ไม่ใช่ Electronics
            Product("Headphones", 500.0, "Electronics")   // ไม่เข้าเงื่อนไข ราคาไม่มากกว่า 500 (เท่ากับพอดี)
        )

        val result = calculateTotalElectronicsPriceOver500(products)

        // 25000.0 + 15000.0 = 40000.0
        assertEquals(40000.0, result, 0.001)
    }

    @Test
    fun `test calculateTotalElectronicsPriceOver500 with no matching products`() {
        val products = listOf(
            Product("Mouse", 300.0, "Electronics"),
            Product("Shirt", 800.0, "Clothing")
        )

        val result = calculateTotalElectronicsPriceOver500(products)

        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun `test calculateTotalElectronicsPriceOver500 with empty list`() {
        val products = emptyList<Product>()

        val result = calculateTotalElectronicsPriceOver500(products)

        assertEquals(0.0, result, 0.001)
    }

// จงเขียน test cases เช็คจำนวนสินค้าที่อยู่ในหมวด 'Electronics' และมีราคามากกว่า 500 บาท
// สมมุติว่ามีฟังก์ชัน countElectronicsProductsOver500(products: List<Product>): Int

    @Test
    fun `test countElectronicsProductsOver500 with mixed products`() {
        val products = listOf(
            Product("Laptop", 25000.0, "Electronics"),
            Product("Mouse", 300.0, "Electronics"),
            Product("Phone", 15000.0, "Electronics"),
            Product("Shirt", 800.0, "Clothing"),
            Product("Headphones", 500.0, "Electronics")
        )

        val result = countElectronicsProductsOver500(products)

        // Laptop, Phone เข้าเงื่อนไข = 2 ชิ้น
        assertEquals(2, result)
    }

    @Test
    fun `test countElectronicsProductsOver500 with no matching products`() {
        val products = listOf(
            Product("Mouse", 300.0, "Electronics"),
            Product("Shirt", 800.0, "Clothing")
        )

        val result = countElectronicsProductsOver500(products)

        assertEquals(0, result)
    }

    @Test
    fun `test countElectronicsProductsOver500 with empty list`() {
        val products = emptyList<Product>()

        val result = countElectronicsProductsOver500(products)

        assertEquals(0, result)
    }
}
// --- Tests for Workshop #2: Data Analysis Pipeline End ---