# Dil Ka Dost — Android native project (Prototype 2.2)

This is the Android Studio source project for an offline-first, native Android prototype. It is **not yet a signed/installable APK**. The current execution environment has Java but no Android SDK, Gradle installation, or Android build tools, so an APK cannot be compiled here. No APK is included or implied.

## Build an APK on a computer
1. Install the latest stable Android Studio on a Windows, macOS, or Linux computer.
2. Open Android Studio → **Open** → select this `DilKaDostAndroid` folder.
3. Allow Gradle sync and install the Android SDK Platform 35 if Android Studio requests it.
4. Select **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
5. Android Studio will show the output location. The debug APK is normally at `app/build/outputs/apk/debug/app-debug.apk`.
6. Transfer that APK to the Android phone and install it. The phone may ask permission to install from that file source. Only install APKs you built or trust.

For a distributable release, create a signed APK from **Build → Generate Signed Bundle / APK**. Keep the signing key private and backed up.

## Included in this prototype
- Native Android interface; no browser wrapper.
- Language selector listing all 22 Eighth Schedule languages plus English (23 options).
- Built-in wording currently available in English and Bengali; the other 21 languages are listed but their reviewed in-app translations are not yet implemented.
- Optional button opens Google Translate externally. It is a convenience link, not an embedded Google translation engine, and it requires internet access. Google Translate coverage differs by language and should be checked in the service.
- Mood, stress, sleep and phone-use check-in plus exam-stage selector.
- Supportive, non-clinical response.
- Prominent **Contact Someone — You Are Not Alone** button on the home screen and a dedicated Contact Centre.
- Four configurable contact categories: trusted person/family, buddy, psychologist/counsellor, and emergency contact. Names and phone numbers are saved locally on the device only.
- Each saved contact has **Call** (opens the phone dialer) and **Message** (opens an SMS draft) actions; the child must confirm the call or send the message.
- Emergency shortcuts for 112 and Tele-MANAS 14416 remain available even without a saved contact.
- The help screen also links directly to the Contact Centre.
- Parent/trusted-adult guidance.
- No account, analytics, ads, automatic messages, background monitoring, or storage of check-in answers. The app opens Google Translate in an external browser only when the user taps the button; no check-in answers are sent to Google.

## Contact Centre setup and safety
A parent or trusted adult should help configure the four contact categories before the child uses the app. The app stores those names and numbers in private app preferences on that phone; it does not upload, sync, or automatically notify anyone. The Call button opens the system dialer rather than placing a call silently. The Message button opens the SMS composer with a suggested message; the child must review and send it. The child should be encouraged to contact a trusted adult nearby if they feel unsafe. The app is not monitored and cannot guarantee that a contact will answer.

## Important safety and scope limits
This is a demonstration prototype, not a clinical tool, diagnostic instrument, suicide-risk predictor, emergency monitoring service, or substitute for qualified human support. It does not automatically contact parents/counsellors and does not provide live counsellor chat. The check-in values are used only to display a supportive response on screen; there is no risk score. The emergency buttons open the phone dialer; the user still has to place the call. Have a qualified child mental-health professional review the wording and safeguarding flow before any real-world student pilot. Verify local helpline availability and translations before deployment.

## Technical notes
- Package: `in.dilkadost.app`
- Version: `2.0` (versionCode 2)
- Minimum Android: 6.0 (API 23)
- Target/compile SDK: 35
- Built with Kotlin and Jetpack Compose.

## Language and translation status
The selector contains: Assamese, Bengali, Bodo, Dogri, Gujarati, Hindi, Kannada, Kashmiri, Konkani, Maithili, Malayalam, Manipuri (Meitei), Marathi, Nepali, Odia, Punjabi, Sanskrit, Santali, Sindhi, Tamil, Telugu, Urdu, and English. The current prototype does **not** yet provide complete built-in translations for all 23 options. Google Translate is opened as a separate service; it is not an embedded extension and does not automatically translate the app screens. Do not use machine-translated emergency or mental-health wording with children until reviewed by native speakers and a qualified child-safeguarding professional.

Google ML Kit on-device translation does not cover all 22 scheduled languages, so it cannot by itself satisfy the full language requirement. See the official Google language-support list: https://developers.google.com/ml-kit/language/translation/translation-language-support.

## Build in GitHub Actions (no local Android Studio required)
1. Create a private GitHub repository and upload the contents of this ZIP, keeping the `DilKaDostAndroid` folder intact.
2. In the repository, open **Actions** and enable workflows if prompted.
3. Select **Build Dil Ka Dost APK** → **Run workflow**.
4. When the run finishes successfully, open that workflow run and download the `Dil-Ka-Dost-debug-APK` artifact. Extract it to obtain `app-debug.apk`.
5. Transfer the APK to the Android phone and install it. This is a debug build for testing, not a Play Store release. Do not share a debug APK as a production release.

The workflow builds the project; it does not add translations or certify the app's safety. All 23 language choices are present, but complete in-app translations remain implemented only in English and Bengali, as described above.
