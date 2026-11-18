package in.dilkadost.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DilKaDostApp() }
    }
}

private enum class Page { HOME, CHECKIN, HELP, CONTACTS, PARENT }

@Composable
private fun DilKaDostApp() {
    var page by remember { mutableStateOf(Page.HOME) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var languageMenuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val bengali = selectedLanguage == "Bengali"
    val languages = listOf(
        "Assamese", "Bengali", "Bodo", "Dogri", "Gujarati", "Hindi", "Kannada", "Kashmiri",
        "Konkani", "Maithili", "Malayalam", "Manipuri (Meitei)", "Marathi", "Nepali", "Odia",
        "Punjabi", "Sanskrit", "Santali", "Sindhi", "Tamil", "Telugu", "Urdu", "English"
    )
    val t: (String, String) -> String = { en, bn -> if (bengali) bn else en }
    var mood by remember { mutableFloatStateOf(3f) }
    var stress by remember { mutableFloatStateOf(3f) }
    var sleep by remember { mutableFloatStateOf(3f) }
    var phone by remember { mutableFloatStateOf(2f) }
    var exam by remember { mutableStateOf("Regular day") }
    var done by remember { mutableStateOf(false) }

    // Contact details are stored only on this device. No contact is notified automatically.
    val prefs = remember { context.getSharedPreferences("dil_ka_dost_contacts", android.content.Context.MODE_PRIVATE) }
    var trustedName by remember { mutableStateOf(prefs.getString("trusted_name", "") ?: "") }
    var trustedPhone by remember { mutableStateOf(prefs.getString("trusted_phone", "") ?: "") }
    var buddyName by remember { mutableStateOf(prefs.getString("buddy_name", "") ?: "") }
    var buddyPhone by remember { mutableStateOf(prefs.getString("buddy_phone", "") ?: "") }
    var counsellorName by remember { mutableStateOf(prefs.getString("counsellor_name", "") ?: "") }
    var counsellorPhone by remember { mutableStateOf(prefs.getString("counsellor_phone", "") ?: "") }
    var emergencyName by remember { mutableStateOf(prefs.getString("emergency_name", "") ?: "") }
    var emergencyPhone by remember { mutableStateOf(prefs.getString("emergency_phone", "") ?: "") }
    var contactsSaved by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = lightColorScheme(
        primary = androidx.compose.ui.graphics.Color(0xFF365D75),
        secondary = androidx.compose.ui.graphics.Color(0xFF568B80),
        background = androidx.compose.ui.graphics.Color(0xFFF5F8F8),
        surface = androidx.compose.ui.graphics.Color.White
    )) {
        Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
            Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(20.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Dil Ka Dost", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text(t("A little check-in. A little support.", "একটু খোঁজ, একটু পাশে থাকা।"), style = MaterialTheme.typography.bodyMedium)
                    }
                    Box {
                        TextButton(onClick = { languageMenuExpanded = true }) {
                            Text("🌐 $selectedLanguage")
                        }
                        DropdownMenu(expanded = languageMenuExpanded, onDismissRequest = { languageMenuExpanded = false }) {
                            languages.forEach { language ->
                                DropdownMenuItem(
                                    text = { Text(language) },
                                    onClick = { selectedLanguage = language; languageMenuExpanded = false }
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(18.dp))
                when (page) {
                    Page.HOME -> {
                        Card(shape = RoundedCornerShape(22.dp)) {
                            Column(Modifier.padding(18.dp)) {
                                Text(t("You matter. Every day.", "তুমি গুরুত্বপূর্ণ—প্রতিদিন।"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(8.dp))
                                Text(t("Take one minute to notice how you are feeling. There is no right or wrong answer.", "এক মিনিট সময় নিয়ে নিজের অনুভূতিটা বুঝে নাও। এখানে ঠিক বা ভুল উত্তর নেই।"))
                                Spacer(Modifier.height(12.dp))
                                Button(onClick = { page = Page.CHECKIN }, modifier = Modifier.fillMaxWidth()) { Text(t("Start my daily check-in", "আজকের খোঁজ নেওয়া শুরু করি")) }
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { page = Page.CONTACTS }, modifier = Modifier.fillMaxWidth()) { Text(t("❤️ Contact Someone — You Are Not Alone", "❤️ কারও সঙ্গে যোগাযোগ করো — তুমি একা নও")) }
                        OutlinedButton(onClick = { page = Page.HELP }, modifier = Modifier.fillMaxWidth()) { Text(t("Emergency help", "জরুরি সাহায্য")) }
                        OutlinedButton(onClick = { page = Page.PARENT }, modifier = Modifier.fillMaxWidth()) { Text(t("Guide for parents and trusted adults", "অভিভাবক ও বিশ্বস্ত বড়দের জন্য নির্দেশিকা")) }
                        OutlinedButton(
                            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://translate.google.com/"))) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Open Google Translate") }
                        Spacer(Modifier.height(12.dp))
                        Note(t("A supportive tool, not a medical or diagnostic service.", "এটি সহায়ক উপকরণ, চিকিৎসা বা রোগ নির্ণয়ের পরিষেবা নয়."))
                        if (selectedLanguage !in listOf("English", "Bengali")) {
                            Spacer(Modifier.height(8.dp))
                            Note("This prototype currently has verified built-in wording in English and Bengali only. Use Google Translate as an optional aid for general text; do not rely on machine translation for emergency or mental-health instructions. The remaining language translations must be added and checked by native speakers before student use.")
                        }
                    }
                    Page.CHECKIN -> {
                        Text(t("Your one-minute check-in", "এক মিনিটের নিজের খোঁজ"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(t("Move each slider to the answer that feels right today.", "আজ তোমার যেমন মনে হচ্ছে, সেভাবে স্লাইডার সরাও।"))
                        Spacer(Modifier.height(12.dp))
                        SliderQuestion(t("How is your mood?", "মন কেমন?"), mood, { mood = it }, t("Low", "খারাপ"), t("Good", "ভালো"))
                        SliderQuestion(t("How stressed do you feel?", "কতটা চাপ অনুভব করছ?"), stress, { stress = it }, t("A little", "একটু"), t("A lot", "অনেক"))
                        SliderQuestion(t("How was your sleep?", "ঘুম কেমন হয়েছে?"), sleep, { sleep = it }, t("Poor", "কম"), t("Good", "ভালো"))
                        SliderQuestion(t("How much time did you spend on your phone?", "ফোনে কতটা সময় কেটেছে?"), phone, { phone = it }, t("Little", "কম"), t("A lot", "অনেক"))
                        Text(t("Today is…", "আজকের দিন…"), fontWeight = FontWeight.SemiBold)
                        listOf("Regular day", "Exam coming", "Exam week", "After an exam").forEach { option ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = exam == option, onClick = { exam = option })
                                Text(t(option, when(option) { "Regular day" -> "সাধারণ দিন"; "Exam coming" -> "পরীক্ষা আসছে"; "Exam week" -> "পরীক্ষার সপ্তাহ"; else -> "পরীক্ষার পরে" }))
                            }
                        }
                        Button(onClick = { done = true }, modifier = Modifier.fillMaxWidth()) { Text(t("Save check-in on this screen", "এই স্ক্রিনে উত্তর দেখাও")) }
                        if (done) {
                            Card(Modifier.fillMaxWidth().padding(top = 12.dp)) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(t("Thank you for checking in.", "নিজের খোঁজ নেওয়ার জন্য ধন্যবাদ।"), fontWeight = FontWeight.Bold)
                                    Text(t("Whatever you selected, your feelings deserve attention. Try a slow breath, drink some water, or talk to someone you trust.", "যাই বেছে নাও, তোমার অনুভূতি গুরুত্ব পাওয়ার যোগ্য। ধীরে শ্বাস নাও, জল খাও, অথবা বিশ্বস্ত কারও সঙ্গে কথা বলো।"))
                                }
                            }
                        }
                        TextButton(onClick = { page = Page.HOME }) { Text(t("Back", "ফিরে যাও")) }
                    }
                    Page.HELP -> {
                        Text(t("You do not have to handle this alone.", "তোমাকে একা সামলাতে হবে না।"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(t("If you are in immediate danger or may hurt yourself, go to a trusted adult now and call emergency services.", "তুমি যদি তাৎক্ষণিক বিপদে থাকো বা নিজেকে আঘাত করতে পারো বলে মনে হয়, এখনই বিশ্বস্ত বড় কারও কাছে যাও এবং জরুরি পরিষেবায় ফোন করো।"))
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { page = Page.CONTACTS }, modifier = Modifier.fillMaxWidth()) { Text(t("Contact my trusted person, buddy or counsellor", "বিশ্বস্ত ব্যক্তি, বন্ধু বা কাউন্সেলরের সঙ্গে যোগাযোগ")) }
                        Button(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"))) }, modifier = Modifier.fillMaxWidth()) { Text(t("Call emergency number 112", "জরুরি নম্বর ১১২-তে ফোন")) }
                        OutlinedButton(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:14416"))) }, modifier = Modifier.fillMaxWidth()) { Text(t("Call Tele-MANAS 14416", "Tele-MANAS ১৪৪১৬-তে ফোন")) }
                        OutlinedButton(onClick = {
                            val msg = t("I am having a difficult time and would like you to stay with me and help me find support.", "আমার সময়টা কঠিন যাচ্ছে। তুমি কি আমার পাশে থাকবে এবং সাহায্য পেতে সাহায্য করবে?")
                            val send = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, msg) }
                            context.startActivity(Intent.createChooser(send, t("Choose someone to contact", "যাঁর সঙ্গে যোগাযোগ করবে তাঁকে বেছে নাও")))
                        }, modifier = Modifier.fillMaxWidth()) { Text(t("Choose a trusted person and share a message", "বিশ্বস্ত কাউকে বেছে নিয়ে বার্তা পাঠাও")) }
                        Spacer(Modifier.height(12.dp))
                        Note(t("This app does not send alerts automatically and is not monitored. If you are unsafe, contact a real person directly.", "এই অ্যাপ নিজে থেকে সতর্কবার্তা পাঠায় না এবং কেউ এটি পর্যবেক্ষণ করেন না। নিরাপদ না বোধ করলে সরাসরি বাস্তব কারও সঙ্গে যোগাযোগ করো।"))
                        TextButton(onClick = { page = Page.HOME }) { Text(t("Back", "ফিরে যাও")) }
                    }
                    Page.CONTACTS -> {
                        Text(t("Contact Someone — You Are Not Alone", "কারও সঙ্গে যোগাযোগ করো — তুমি একা নও"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(t("Choose someone you trust. You can call or write a message. Nothing is sent until you choose to send it.", "তুমি যাঁকে বিশ্বাস করো তাঁকে বেছে নাও। ফোন করতে বা বার্তা লিখতে পারো। তুমি নিজে না পাঠালে কিছুই পাঠানো হবে না।"))
                        Spacer(Modifier.height(12.dp))
                        Text(t("Set up contacts (with a parent or trusted adult)", "যোগাযোগের তথ্য যোগ করো (অভিভাবক বা বিশ্বস্ত বড় কারও সাহায্যে)"), fontWeight = FontWeight.SemiBold)
                        ContactEditor(t("Trusted person / family", "বিশ্বস্ত ব্যক্তি / পরিবার"), trustedName, { trustedName = it }, trustedPhone, { trustedPhone = it })
                        ContactEditor(t("My buddy", "আমার বন্ধু"), buddyName, { buddyName = it }, buddyPhone, { buddyPhone = it })
                        ContactEditor(t("Psychologist / counsellor", "মনোবিজ্ঞানী / কাউন্সেলর"), counsellorName, { counsellorName = it }, counsellorPhone, { counsellorPhone = it })
                        ContactEditor(t("Emergency contact", "জরুরি যোগাযোগ"), emergencyName, { emergencyName = it }, emergencyPhone, { emergencyPhone = it })
                        Button(onClick = {
                            prefs.edit()
                                .putString("trusted_name", trustedName).putString("trusted_phone", trustedPhone)
                                .putString("buddy_name", buddyName).putString("buddy_phone", buddyPhone)
                                .putString("counsellor_name", counsellorName).putString("counsellor_phone", counsellorPhone)
                                .putString("emergency_name", emergencyName).putString("emergency_phone", emergencyPhone)
                                .apply()
                            contactsSaved = true
                        }, modifier = Modifier.fillMaxWidth()) { Text(t("Save contacts on this phone", "এই ফোনে যোগাযোগের তথ্য সংরক্ষণ করো")) }
                        if (contactsSaved) Note(t("Contacts saved on this device only.", "যোগাযোগের তথ্য শুধু এই ডিভাইসেই সংরক্ষিত হয়েছে।"))
                        Spacer(Modifier.height(12.dp))
                        Text(t("Contact now", "এখন যোগাযোগ করো"), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        ContactActions(t("Trusted person / family", "বিশ্বস্ত ব্যক্তি / পরিবার"), trustedName, trustedPhone, context)
                        ContactActions(t("My buddy", "আমার বন্ধু"), buddyName, buddyPhone, context)
                        ContactActions(t("Psychologist / counsellor", "মনোবিজ্ঞানী / কাউন্সেলর"), counsellorName, counsellorPhone, context)
                        ContactActions(t("Emergency contact", "জরুরি যোগাযোগ"), emergencyName, emergencyPhone, context)
                        Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                            Column(Modifier.padding(14.dp)) {
                                Text(t("Immediate danger?", "তাৎক্ষণিক বিপদ?"), fontWeight = FontWeight.Bold)
                                Text(t("Call 112 for emergency services. Tele-MANAS 14416 offers mental-health support.", "জরুরি পরিষেবার জন্য ১১২ নম্বরে ফোন করো। মানসিক স্বাস্থ্য সহায়তার জন্য Tele-MANAS ১৪৪১৬।"))
                                Button(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"))) }, modifier = Modifier.fillMaxWidth()) { Text(t("Open dialer — 112", "ডায়ালার খোলো — ১১২")) }
                                OutlinedButton(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:14416"))) }, modifier = Modifier.fillMaxWidth()) { Text(t("Open dialer — Tele-MANAS 14416", "ডায়ালার খোলো — Tele-MANAS ১৪৪১৬")) }
                            }
                        }
                        Note(t("If you feel unsafe, go to a trusted adult nearby and ask them to stay with you. The app is not monitored and does not send automatic alerts.", "নিজেকে অনিরাপদ মনে হলে কাছের বিশ্বস্ত বড় কারও কাছে যাও এবং পাশে থাকতে বলো। অ্যাপটি কেউ পর্যবেক্ষণ করেন না এবং নিজে থেকে সতর্কবার্তা পাঠায় না।"))
                        TextButton(onClick = { page = Page.HOME }) { Text(t("Back", "ফিরে যাও")) }
                    }
                    Page.PARENT -> {
                        Text(t("A guide for parents and trusted adults", "অভিভাবক ও বিশ্বস্ত বড়দের জন্য"), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        listOf(
                            t("Listen calmly. Let the child speak without interruption or judgement.", "শান্তভাবে শোনো। বাধা বা বিচার না করে শিশুকে বলতে দাও।"),
                            t("Ask open questions: ‘What has felt difficult today?’", "খোলামেলা প্রশ্ন করো: ‘আজ কোন বিষয়টা কঠিন লেগেছে?’"),
                            t("Take talk of hopelessness or self-harm seriously. Stay with the child and seek qualified help promptly.", "হতাশা বা নিজেকে আঘাত করার কথা গুরুত্ব দিয়ে নাও। শিশুর পাশে থাকো এবং দ্রুত যোগ্য সহায়তা নাও।"),
                            t("Do not use app answers to punish, label, diagnose, or monitor a child.", "অ্যাপের উত্তর দিয়ে শিশুকে শাস্তি, দোষারোপ, রোগ নির্ণয় বা নজরদারি কোরো না।"),
                            t("For immediate danger in India, call 112. Tele-MANAS: 14416.", "ভারতে তাৎক্ষণিক বিপদে ১১২ নম্বরে ফোন করো। Tele-MANAS: ১৪৪১৬।")
                        ).forEach { item -> Card(Modifier.fillMaxWidth().padding(vertical = 5.dp)) { Text(item, Modifier.padding(14.dp)) } }
                        TextButton(onClick = { page = Page.HOME }) { Text(t("Back", "ফিরে যাও")) }
                    }
                }
                Spacer(Modifier.height(18.dp))
                Text("Dil Ka Dost • Prototype 2.1 • Language-access demo", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun ContactEditor(title: String, name: String, onName: (String) -> Unit, phone: String, onPhone: (String) -> Unit) {
    Card(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(value = name, onValueChange = onName, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = phone, onValueChange = onPhone, label = { Text("Phone number (with country code if needed)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        }
    }
}

@Composable
private fun ContactActions(title: String, name: String, phone: String, context: android.content.Context) {
    if (phone.isNotBlank()) {
        Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Column(Modifier.padding(12.dp)) {
                Text(if (name.isBlank()) title else "$title — $name", fontWeight = FontWeight.SemiBold)
                Text(phone, style = MaterialTheme.typography.bodySmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phone)}"))) }, modifier = Modifier.weight(1f)) { Text("Call") }
                    OutlinedButton(onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${Uri.encode(phone)}")).apply {
                            putExtra("sms_body", "I need someone to talk to. Can you please contact me?")
                        }
                        context.startActivity(intent)
                    }, modifier = Modifier.weight(1f)) { Text("Message") }
                }
            }
        }
    }
}

@Composable
private fun SliderQuestion(title: String, value: Float, onValue: (Float) -> Unit, low: String, high: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Slider(value = value, onValueChange = onValue, valueRange = 1f..5f, steps = 3)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(low, style = MaterialTheme.typography.labelSmall); Text(high, style = MaterialTheme.typography.labelSmall) }
    }
}

@Composable
private fun Note(text: String) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer), shape = RoundedCornerShape(16.dp)) {
        Text(text, Modifier.padding(14.dp), style = MaterialTheme.typography.bodyMedium)
    }
}
