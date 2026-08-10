# Workshop #2 — สรุปสิ่งที่เรียนรู้
## Data Class + Collections (List/Sequence) + filter/map/sum

---

## 1. `data class` และ auto-generated `toString()`

```kotlin
data class Product(val name: String, val price: Double, val category: String)
```

- `data class` ให้ Kotlin auto-generate `toString()`, `equals()`, `hashCode()`, `copy()` ให้อัตโนมัติ
- เพราะมี `toString()` ให้แล้ว `println(product)` หรือ `forEach { println(it) }` จะ print ออกมาอ่านง่ายทันที เช่น `Product(name=Laptop, price=35000.0, category=Electronics)` โดยไม่ต้องเขียน `toString()` เอง

## 2. การสร้าง `List<Product>` และ `forEach`

```kotlin
val products = listOf(
    Product("Laptop", 35000.0, "Electronics"),
    ...
)

products.forEach { println(it) }
```

- `listOf(...)` สร้าง immutable list จาก object ที่ประกาศเรียงตาม constructor (`name, price, category`)
- `forEach { }` วน loop ทุก element โดย `it` คือตัวแปร default ของ lambda ที่มี parameter เดียว (เทียบเท่า `forEach { product -> println(product) }`)

## 3. Chaining operations บน `List` — `filter → filter → map → sum`

```kotlin
val totalElecPriceOver500 = products
    .filter { it.category == "Electronics" }
    .filter { it.price > 500 }
    .map { it.price }
    .sum()
```

- `filter { }` คืน list ใหม่ที่มีแค่ element ที่เงื่อนไขเป็น `true`
- `map { }` แปลงแต่ละ element เป็นค่าอื่น (ในที่นี้ดึงเฉพาะ `price` ออกมาเป็น `List<Double>`)
- `sum()` รวมค่าทั้งหมดใน list ตัวเลข

**ทำไมแยก `.filter` สองรอบ แทนที่จะรวมเงื่อนไขในตัวเดียว (`category == "Electronics" && price > 500`)?**
ผลลัพธ์สุดท้ายเหมือนกันทุกประการ แต่ต่างกันที่:
- **Readability** — แยกเป็นคนละบรรทัด แต่ละบรรทัดมีหน้าที่ชัดเจน ง่ายต่อการ debug (เช็คได้ทีละขั้นว่าหลัง filter category เหลืออะไร)
- **Performance บน `List`** — filter สองรอบสร้าง **intermediate List สองใบ** (List หลัง filter category, List หลัง filter price) ก่อนจะไปต่อ `map` และ `sum` ในขณะที่ filter รวมเงื่อนไขเดียวสร้างแค่ List เดียว เพราะ `List` operations เป็นแบบ **eager** — ทำงานและสร้าง collection ใหม่ทันทีทุกครั้งที่เรียก operation

## 4. `.asSequence()` — Lazy evaluation

```kotlin
val totalElecPriceOver500Sequence = products
    .asSequence()
    .filter { it.category == "Electronics" }
    .filter { it.price > 500 }
    .map { it.price }
    .sum()
```

- `.asSequence()` แปลง `List` เป็น `Sequence` ก่อนเริ่ม chain — โครงโค้ดเหมือนเดิมทุกอย่าง ต่างแค่จุดเริ่มต้น
- **Sequence เป็นแบบ lazy**: `filter`, `map` จะไม่ทำงานทันที แต่ถูกเรียงเป็น pipeline ไว้ก่อน แล้วรอ **terminal operation** (ในที่นี้คือ `.sum()`) มาสั่งให้เริ่มประมวลผลจริง
- เมื่อเริ่มประมวลผล ข้อมูลแต่ละ element จะไหลผ่านทุกขั้นตอนของ pipeline ทีละตัว (เช่น `Laptop` ผ่าน filter category → filter price → map ให้เสร็จก่อน แล้ว `Smartphone` ถึงเริ่ม) แทนที่จะทำทีละขั้นตอนกับข้อมูลทั้งชุด
- ผลคือ **ไม่มีการสร้าง intermediate collection กลางทาง** — ประหยัดหน่วยความจำและเร็วกว่าสำหรับข้อมูลขนาดใหญ่ (หลักล้าน records) แม้จะเขียน `.filter` สองรอบแยกกันเหมือนเดิมก็ตาม

## 5. สรุปเปรียบเทียบ

| | List | Sequence |
|---|---|---|
| Evaluation | Eager (ทำทันทีทุก operation) | Lazy (รอ terminal operation) |
| Intermediate collection | สร้างใหม่ทุกขั้นตอน | ไม่สร้าง ประมวลผลทีละ element ผ่านทั้ง pipeline |
| เหมาะกับ | ข้อมูลขนาดเล็ก, อ่านง่าย | ข้อมูลขนาดใหญ่, ต้องการ performance |

---

## บันทึกสำหรับ AI Log

**สิ่งที่ AI ช่วย:** อธิบาย syntax `listOf`/`data class`/`filter`/`map`/`sum`/`asSequence` แบบทีละขั้น และถามนำให้ผู้เรียนอธิบายเหตุผลด้าน performance ระหว่าง filter สองรอบ vs filter รวมเงื่อนไข ด้วยตัวเอง — AI ไม่ได้เขียนโค้ดให้ ผู้เรียนพิมพ์เองทุกบรรทัด

**จุดที่ผู้เรียนอธิบายได้ชัดเจนเอง:** ความแตกต่างระหว่าง eager (List) กับ lazy (Sequence) evaluation และเหตุผลที่ intermediate List เกิดขึ้นเมื่อ chain filter หลายรอบบน List ธรรมดา

**AI แก้ผิดตรงไหนไหม:** ไม่มีในรอบนี้