Vitrini
Vitrini is an Android application project built with Kotlin, designed to explore how local entrepreneurs in Costa Rica could connect with potential customers through a mobile discovery experience.
Project status: Early-stage collaborative academic project. The current repository README describes the initial Android setup, MVVM organization, base theme, and preliminary data models. The full product experience below is the project vision, not a claim that every feature is functional.

Product vision
Vitrini explores three complementary experiences:
- Business discovery: Help people discover local entrepreneurs and their products.
- Commerce-inspired browsing: Make small businesses easier to explore through a mobile catalog-like experience.
- Interest-based matching: Connect potential customers with relevant businesses.
These areas describe intended functionality. Consult the source code for features currently implemented.
Technical overview
Area	Technology / approach
Platform	Android
Language	Kotlin
IDE	Android Studio
Design approach	MVVM (Model–View–ViewModel)
Collaboration	Git / GitHub
Persistence	Planned; not claimed as implemented


The original project documentation describes these architectural responsibilities:
- data/ — local or simulated data sources
- model/ — application data models
- repository/ — data-access abstraction
- ui/ — UI screens and components
- viewmodel/ — presentation logic
- utils/ — shared constants and utilities
This reflects the intended project organization; consult the repository tree for the exact directories present in the current commit.
Documented project stage
The repository's initial documentation identifies:
- Android project initialization
- Initial MVVM structure
- Basic visual theme
- Initial data models
The complete discovery, shopping, and matching experiences remain project goals unless demonstrated in the source.
Getting started
1. Install Android Studio and the required Android SDK components.
2. Clone the repository:
   git clone https://github.com/SofL22/Vitrini.git
3. Open the cloned project in Android Studio and allow Gradle synchronization.
4. Inspect the available build/run configurations and required SDK versions in the project files.
5. Build or run the available application configuration, if the checked-in project supports it.
The current documentation does not establish a tested build command or a production-ready application. No APK, live demo, or completed release is claimed.

Collaboration and authorship
Vitrini is a team project. This repository is presented as evidence of participation in Android development and collaborative project work, not as a claim that one contributor independently implemented the entire application.
Next steps
- Document implemented screens with real screenshots.
- Complete and verify the planned discovery and business workflows.
- Add reproducible build instructions and testing notes.
- Identify contributor roles and contributions with the team.
