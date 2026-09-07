# LoboAndChoco

Instagram-style Android app written in Kotlin.  
Kotlin으로 만든 인스타그램 스타일 Android 앱입니다.

GitHub repo: [chocoandlobo](https://github.com/Lobo2u/chocoandlobo) · App name: **LoboAndChoco** (`com.example.loboandchoco`)

[English](#english) | [한국어](#한국어)

---

## English

### Status

This is a **personal learning project** (UI clone / experiment), not a finished product.  
Several tabs and screens are placeholders. Some upload/Firebase work is unfinished.

Author: **Lobo2u / Eunsoo Jo**

### What the code does

Bottom navigation (`MainActivity`) switches these destinations:

| Tab / screen | What is actually implemented |
| --- | --- |
| **Home** | Horizontal story row (hardcoded dummy “Choco” items) + vertical feed loaded from Firebase Realtime Database path `FeedList`. Images via Glide. Like toggles `likeCount` in Firebase. Bookmark toggles locally only. Comment icon opens `CommentActivity`. |
| **Search** | 3-column grid of hardcoded image URLs. Tapping the search bar opens `SearchActivity`. |
| **SearchActivity** | Type a keyword and press Enter to save it. Recent keywords are stored in `SharedPreferences` (`LoboAndChoco` / `keywords`) with Gson. Items can be removed. This does **not** search posts. |
| **Upload** | On open, picks an image/video from storage. Tapping the preview launches the camera. Caption field is on screen; “사람 태그하기” / “위치 추가” are labels only. Complete (`upload_btn_complete`) just `finish()`es — Firebase write is commented `//Firebase` and is not implemented. |
| **Heart (좋아요)** | Placeholder fragment that shows the text `Heart`. |
| **My Page (마이페이지)** | Placeholder fragment that shows the text `mypage`. |
| **Comment** | Placeholder activity that shows `댓글화면`. |

`MainFragment` is leftover Android Studio template code and is not used by navigation.

Permissions declared: `INTERNET`, `CAMERA`, `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`.

### Project structure

Single-module Gradle project (`settings.gradle` → `rootProject.name = "LoboAndChoco"`, include `:app`).

```
.
├── app/
│   ├── build.gradle              # Android app module, View Binding, dependencies
│   ├── google-services.json      # Firebase Android config (plugin not applied; see Setup)
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/loboandchoco/
│       │   ├── MainActivity.kt
│       │   ├── HomeFragment.kt, SearchFragment.kt, HeartFragment.kt, MyPageFragment.kt
│       │   ├── SearchActivity.kt, UploadActivity.kt, CommentActivity.kt
│       │   ├── Feed.kt, Story.kt
│       │   └── FeedAdapter.kt, StroyAdapter.kt, RecommendAdapter.kt, KeywordAdapter.kt
│       └── res/                  # layouts, menu, drawables, strings (Korean UI copy)
├── build.gradle                  # AGP 7.1.1, Kotlin 1.6.21
├── settings.gradle
├── gradle.properties
└── gradle/wrapper/               # Gradle 7.2
```

### Stack (from Gradle)

- Kotlin 1.6.21, Android Gradle Plugin 7.1.1, Gradle 7.2
- `compileSdk` / `targetSdk` 32, `minSdk` 21
- AndroidX AppCompat, Material, ConstraintLayout, View Binding
- Glide 4.11.0, Gson 2.8.5
- Firebase BoM 29.3.1: `firebase-database-ktx`, `firebase-analytics-ktx`

Default template JUnit / Espresso tests only (`ExampleUnitTest`, `ExampleInstrumentedTest`).

### Setup

1. Install [Android Studio](https://developer.android.com/studio) that can import AGP 7.1 (JDK **11** — this repo’s `.idea` uses language level JDK 11).
2. Clone the repo and open the **root** folder (the one with `settings.gradle`).
3. Let Gradle sync. Wrapper: `./gradlew` (Unix) or `gradlew.bat` (Windows).
4. Connect a device or start an emulator (API 21+).
5. Run the `app` configuration, or:

```bash
./gradlew assembleDebug
```

Debug APK: `app/build/outputs/apk/debug/app-debug.apk`.

#### Firebase (home feed)

Home reads `FeedList` from Firebase Realtime Database. Expected fields per item: `userId`, `imageUrl`, `profileImageUrl`, `likeCount`, `isLike`, `isBookmark`. The listener skips index `0` and starts at `1`.

`app/google-services.json` is already in the tree, but **`com.google.gms.google-services` is not applied** in the root or `app` `build.gradle`. You may need to add that plugin (and a matching classpath) for Firebase to initialize from that file. Without a reachable DB (or if init fails), the home feed can stay empty or error.

Upload does not write posts yet.

---

## 한국어

### 상태

완성된 서비스가 아니라 **개인 학습용 실험/클론**입니다.  
일부 탭은 자리만 있고, 업로드·Firebase 연동은 일부만 되어 있습니다.

작성자: **Lobo2u / Eunsoo Jo**

### 코드 기준으로 구현된 기능

`MainActivity` 하단 탭으로 화면을 바꿉니다.

| 화면 | 실제 구현 |
| --- | --- |
| **홈** | 가로 스토리(하드코딩된 “Choco” 더미) + Firebase Realtime Database `FeedList` 피드. 이미지는 Glide. 좋아요는 Firebase `likeCount`를 갱신. 북마크는 로컬 UI만. 댓글 아이콘은 `CommentActivity`를 엽니다. |
| **검색** | 하드코딩 이미지 URL을 3열 그리드로 표시. 검색 바를 누르면 `SearchActivity`. |
| **SearchActivity** | Enter로 키워드를 저장. 최근 검색어는 `SharedPreferences`(`LoboAndChoco` / `keywords`) + Gson. 삭제 가능. **게시물 검색은 없음.** |
| **업로드** | 시작 시 저장소에서 이미지/영상 선택. 미리보기를 누르면 카메라. 문구 입력란은 있고, “사람 태그하기” / “위치 추가”는 라벨만. 완료는 `finish()`만 호출 (`//Firebase` 주석, 업로드 미구현). |
| **좋아요** | `Heart` 텍스트만 있는 플레이스홀더. |
| **마이페이지** | `mypage` 텍스트만 있는 플레이스홀더. |
| **댓글** | `댓글화면` 텍스트만 있는 플레이스홀더. |

`MainFragment`는 Android Studio 템플릿 잔여물로, 네비게이션에서 쓰이지 않습니다.

권한: `INTERNET`, `CAMERA`, `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`.

### 프로젝트 구조

모듈은 `:app` 하나입니다 (`rootProject.name = "LoboAndChoco"`). 디렉터리 안내는 위의 [Project structure](#project-structure)와 같습니다.

### 사용 기술 (Gradle 기준)

- Kotlin 1.6.21, AGP 7.1.1, Gradle 7.2
- `compileSdk` / `targetSdk` 32, `minSdk` 21
- AndroidX, Material, View Binding, Glide, Gson
- Firebase BoM 29.3.1 (Realtime Database, Analytics)

테스트는 템플릿 예제만 있습니다.

### 실행 방법

1. AGP 7.1을 열 수 있는 [Android Studio](https://developer.android.com/studio)와 **JDK 11**을 준비합니다.
2. 저장소를 클론한 뒤 `settings.gradle`이 있는 **루트**를 엽니다.
3. Gradle Sync를 기다립니다.
4. API 21 이상 기기/에뮬레이터에서 `app`을 Run 하거나:

```bash
./gradlew assembleDebug
```

#### Firebase (홈 피드)

홈은 Realtime Database의 `FeedList`를 읽습니다. 항목 필드: `userId`, `imageUrl`, `profileImageUrl`, `likeCount`, `isLike`, `isBookmark`. 리스너는 인덱스 `0`을 건너뛰고 `1`부터 사용합니다.

`app/google-services.json`은 있으나 **Google Services Gradle 플러그인이 적용되어 있지 않습니다.** Firebase 초기화가 필요하면 플러그인을 추가해야 할 수 있습니다. DB에 연결되지 않으면 홈 피드가 비어 있거나 오류가 날 수 있습니다.

업로드로 게시물을 저장하는 코드는 아직 없습니다.
