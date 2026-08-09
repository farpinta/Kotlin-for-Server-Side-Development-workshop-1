# Workshop #1 — สรุปสิ่งที่เรียนรู้
## พื้นฐานภาษา Kotlin + Null Safety

---

## 1. `when` expression (แทน `switch` ใน Java)

```kotlin
when (choice) {
    "1" -> convertCelsiusToFahrenheit()
    "2" -> convertKilometersToMiles()
    "exit" -> {
        println("พิมพ์ 'exit' เพื่อออกจากโปร")
        break
    }
    else -> println("ไปพิมพ์ใหม่จ้ะ")
}
```

- แต่ละ branch เทียบค่ากับตัวแปรใน `()` แบบตรงตัว ไม่ต้องมี `break` ท้ายทุก case เหมือน Java (ไม่มี fall-through)
- ถ้าอยากทำหลาย statement ใน branch เดียว ใช้ `{ }` ครอบ เช่น branch `"exit"`
- `else` จำเป็นถ้าใช้ `when` แบบ statement ครอบคุมทุกกรณีไม่ครบ (แต่ถ้าใช้แบบ expression ที่ต้องคืนค่า Kotlin จะบังคับให้ครบทุกกรณี)
- `break` ใช้ออกจาก loop ที่ใกล้ที่สุด (`while (true)` ในที่นี้) ทันที ไม่ใช่ออกจาก `when`

## 2. การประกาศฟังก์ชันและ parameter

```kotlin
fun celsiusToFahrenheit(celsius: Double): Double {
    return celsius * 9.0 / 5.0 + 32
}
```

- Kotlin เขียน parameter แบบ `ชื่อ: ชนิดข้อมูล` (สลับกับ Java ที่เขียน `Type name`)
- Return type เขียนหลังวงเล็บ `()` โดยใช้ `:` คั่น เช่น `): Double {`
- ใช้ `9.0` และ `5.0` (ไม่ใช่ `9`, `5`) เพื่อบังคับให้เป็น `Double` ตั้งแต่ต้น ป้องกัน integer division ที่จะตัดเศษทิ้ง

## 3. Null Safety — `toDoubleOrNull()` + Elvis operator (`?:`)

```kotlin
val celsius = input.toDoubleOrNull() ?: run {
    print("โปรดใส่เลขครับ")
    return
}
```

- `input` เป็น `String`, `toDoubleOrNull()` คืนค่าเป็น `Double?` (แปลงไม่ได้ = `null` แทนที่จะ throw exception เหมือน `toDouble()` เฉยๆ)
- `?:` (Elvis operator) อ่านว่า "ถ้าฝั่งซ้ายเป็น null ให้ใช้ค่า/ทำสิ่งที่อยู่ฝั่งขวาแทน"
- `run { }` ใช้ครอบเมื่ออยากทำหลาย statement ฝั่งขวาของ `?:` (ในที่นี้คือ print ข้อความ แล้ว `return` ออกจากฟังก์ชัน)
- ผลลัพธ์: `celsius` ที่ได้เป็น `Double` ล้วนๆ (ไม่ใช่ `Double?`) พร้อมส่งเข้าฟังก์ชันแปลงหน่วยได้ทันที ไม่ต้อง null-check ซ้ำอีก

## 4. `val` vs `var`

ทุกตัวแปรในไฟล์นี้ใช้ `val` เพราะไม่มีตัวไหนถูกกำหนดค่าใหม่หลังประกาศ — Kotlin แนะนำให้เริ่มจาก `val` เสมอ (immutability by default) แล้วเปลี่ยนเป็น `var` เฉพาะเมื่อจำเป็นต้องเปลี่ยนค่าจริงๆ

## 5. จุดที่ควรปรับปรุง (ยังไม่แก้ในเวอร์ชันนี้)

- ใช้ `print` แทน `println` ในข้อความ error ("โปรดใส่เลขครับ") ทำให้ไม่มีขึ้นบรรทัดใหม่ ควรเปลี่ยนเป็น `println`
- `convertCelsiusToFahrenheit()` และ `convertKilometersToMiles()` มีโครงสร้างซ้ำกันเกือบทั้งหมด — เป็นประเด็นที่น่าคิดต่อเรื่อง code reuse ในบทถัดไป (ยังไม่ต้องแก้ตอนนี้)

---

## บันทึกสำหรับ AI Log

**สิ่งที่ AI ช่วย:** อธิบาย concept (`when`, syntax การประกาศฟังก์ชัน, `toDoubleOrNull()`, Elvis operator) แบบทีละขั้น ไม่ได้เขียนโค้ดให้ — ผู้เรียนพิมพ์เองทุกบรรทัดหลังจากได้คำใบ้

**AI แก้ผิดตรงไหนไหม:** ไม่มี ในรอบนี้ AI เพียงถามนำและชี้จุดที่ syntax ยังไม่ครบ (เช่น parameter ไม่มี type, exit case ไม่มี `break`) ให้ผู้เรียนแก้เอง