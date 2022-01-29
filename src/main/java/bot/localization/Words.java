package bot.localization;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Words {

    //===============================>MENU_WORDS<===============================\\
    ABOUT("""
            <i>
            Ushbu bot daza.uz kompaniyasining sotuv ishlarini monitoring qilish uchun yaratilgan.
            Kompaniya haqida ma'lumot olish uchun saytga tashrif buyuring</i>"""),
    CONGRATULATION("<b>Bo'tga a'zo bo'lganingiz bilan tabriklayman 😉</b>"),
    REQUEST_SENT("<b>So'rovingiz yuborildi ✅</b>"),
    REQUEST_EMPTY("<b>So'nggi so'rovlar mavjud emas</b>"),
    ACTIVE_USERS_EMPTY("<b>Aktiv foydalanuvchilar mavjud emas</b>"),
    PRESS_FOR_ACTIVATE("<b>Guruhlarni faollashtirish uchun  ularning ustiga bosing</b>"),
    ALL_GROUPS_ACTIVATED("<b>Barcha guruhlar faollashtirilgan</b>"),
    ALL_GROUPS_ACTIVATED1("<b>Barcha guruhlar faollashtirildi</b>"),
    ALL_GROUPS_DELETED("<b>Barcha guruhlar o'chirildi!</b>"),
    PRESS_FOR_DELETE("<b>Guruhlarni olib tashlash uchun ularni ustiga bosing</b>"),
    EMPTY_ACTIVE_GROUPS("<b>Faol guruhlar mavjud emas</b>"),
    ENTER_PHONE_FADD("""
            <b>Qo'shmoqchi bo'lgan ishchingizning telefon raqamini quyidagi formatda kiriting 👇</b>              
            <u>998001234567</u>
            """),
    ENTER_PHONE_FREMOVE("""
            <b>Bloklamoqchi bo'lgan ishchingizning telefon raqamini quyidagi formatda kiriting 👇</b>            
            <u>998001234567</u>
            """),
    BLOCKED("<b>Foydalanuvchi bloklandi ✅</b>"),
    BLOCKED_BY("<b>Admin tomonidan bloklandingiz ⛔️</b>"),
    SEND_FOR_SEND("<b>Guruhga yuborish uchun kerakli ma'lumotlarni jo'nating</b>"),
    YOU_ARE_BLOCKED("<b>Siz bloklangansiz</b>"),
    WRONG_COMMAND("<b>Kechirasiz! tushunmadim :(</b>"),
    USER_NOT_FOUND("<b>Bu raqam bilan botga hech kim azo bo'lmagan</b>"),
    SUCCESSFULLY_ADDED("<b>Muvaffaqiyatli qo'shildi ✅</b>"),
    SUCCESSFULLY_SENT("<b>Xabaringiz yuborildi ✅</b>"),
    WRONG_SENT("<b>Xabaringiz yuborilmadi ❌</b>"),
    ACTIVE_USERS_LIST("<b>Ayni vaqtda ishchi vazifasidagi va bloklanmagan foydalanuvchilar ro'yxati\n\n</b>"),
    USER_DETAILS("<b>%s. Tahallus: <a href=\"tg://user?id=%s\">%s</a>\n⠷ Telefon: <code>%s</code></b>\n\n"),
    REQ_DETAILS("<b>Yuboruvchi: <a href=\"tg://user?id=%s\">%s</a>\nTelefon raqam: </b><code>%s</code>\n<b>Yuborildi: %s</b>"),
    HI("<b>Hayrli kun</b>"),
    SEND_PHONE("<b>Telefon raqamingizni yuboring 👇</b>"),
    JOINED("""
            <b>Assalomu alaykum</b>
            <i>Admin tomonidan qabul qilindingiz!Ishni boshlash uchun /start bosing</i>"""),
    WRONG_PHONE("<b>Iltimos to'g'ri raqam kiriting</b>"),
    NOTIFIY("""
            <i>Adminlar sizni tasdiqlashsa bu haqida xabar beramiz.
            Agar 24 soat ichida xabar kelmasa /request buyrug'i bilan yana so'rov yuborib ko'ring.
            Kungingiz xayrli o'tsin :)</i>""");
    private final String uz;

    public String get(String language) {
        return this.uz;
    }
}
