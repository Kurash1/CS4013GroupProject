# CS4013 – Student Records System
## Member 1: Integration Instructions

This README describes how to connect the five student-side classes in `Integrated_CS4013.zip` to the other group members' implementations. It documents the current interfaces and outstanding integration requirements, rather than proposing replacement implementations.

## 1. Files to integrate

Copy the following files from the integrated archive into the group repository's `src/main/java/` directory, replacing older versions of these same classes only. Review any concurrent changes before replacing files.

| Class | Responsibility |
| --- | --- |
| `Student.java` | Stores one student's personal and contact information. |
| `Semester.java` | Stores one semester's module results and calculates semester QCA. |
| `Transcript.java` | Combines student, programme, semester, progression and award information. |
| `StudentMenu.java` | Provides the student-facing command-line interface. |
| `StudentRecordsSimulation.java` | Demonstrates startup and authentication using temporary sample data. |

The files use the default Java package. The supplied Maven configuration targets Java 17. Do not replace other members' classes with older copies from the archive.

## 2. Member 4 – Authentication, permissions and persistence

### 2.1 Student construction

The constructor is unchanged:

```java
Student student = new Student(firstName, lastName, studentID,
    email, termTimeAddress, correspondenceAddress, phoneNumber);
```

All seven arguments are `String` values. There is no public setter for student ID. The system-management layer must load and manage the student collection; `Student` represents one record only.

### 2.2 Updated Student method signatures

Obtain the permissions from the authenticated login:

```java
EnumSet<Permission> permissions = login.getPermissions();
String id = student.getStudentID(permissions);
String email = student.getEmail(permissions);
```

All getters now accept `EnumSet<Permission> permissions` and declare `throws Exception`:

- `getFirstName`, `getLastName`, `getStudentID`, `getEmail`
- `getTermTimeAddress`, `getCorrespondenceAddress`, `getPhoneNumber`

All six setters accept the new `String` value first and permissions second, and declare `throws Exception`:

- `setFirstName`, `setLastName`, `setEmail`, `setPhoneNumber`
- `setTermTimeAddress`, `setCorrespondenceAddress`

Example: `student.setEmail(newEmail, permissions);` requires `writeStudent`.

The getters use `Permission.CheckOrFail(permissions, Permission.readStudent)`; the setters use `Permission.CheckOrFail(permissions, Permission.writeStudent)`. Replace old zero-argument getter calls and one-argument setter calls throughout dependent code. Handle or propagate exceptions.

For formatted personal information, use `student.displayStudentDetails(permissions)` rather than `student.toString()`. The detailed `toString()` override has been removed.

### 2.3 Authenticate before opening the menu

The following example assumes that the system has already retrieved the appropriate `User`, password input, matching `Student` and matching `Transcript`:

```java
Login login = Login.TryLogin(user, password);
if (login != null) {
    StudentMenu menu = new StudentMenu(student, transcript, login);
    menu.displayMenu();
}
```

`Login.TryLogin` declares `throws Exception` and returns `null` for an unsuccessful password comparison. `StudentMenu` clones the permission set from the supplied login. The system must verify that the student and transcript belong to the authenticated account before passing them to the menu. The current menu constructor does not perform this ownership check.

### 2.4 Outstanding system integration

- Connect authenticated account lookup to the corresponding student record and transcript.
- Replace the temporary sample startup in `StudentRecordsSimulation` with the real authentication and record-loading workflow. `App.java` remains a separate Hello World entry point in this snapshot.
- Agree an ownership-aware mechanism for students to edit their own permitted personal details. The existing `STUDENT_PERMISSIONS` contains `readStudent`, `readProgramme` and `readModule`, but not `writeStudent`. Consequently, menu option 2 currently rejects student accounts. Do not grant unrestricted write access or use `SUPER_PERMISSIONS` to bypass this restriction.
- Connect successful updates to CSV persistence. The current update menu changes values in memory when authorized, then throws `UnsupportedOperationException` because saving is not implemented.
- Supply the official registered-module list and validated optional-module registration workflow for menu options 4 and 5. Both currently throw `UnsupportedOperationException`.
- Ensure academic record mutation and access are authorized by the system. `Semester` and several `Transcript` methods do not currently enforce method-level permissions or ownership themselves.

## 3. Member 2 – Programme and Module

### 3.1 Programme interface

`Transcript` requires a non-null `Programme` object in its constructor. Both the transcript and student menu currently use `programme.toString()` to display programme information. Supply a meaningful representation, or agree a replacement display interface if the programme implementation uses permission-protected accessors.

The student menu retrieves its programme using `transcript.getProgramme()`; no separate programme field is required in `Student`.

For optional module selection, the programme implementation must supply eligible modules and selection rules. The exact registration API has not yet been agreed in this snapshot.

### 3.2 Module interface

The semester and transcript implementations require a custom `Module` class with:

| Expected method | Purpose |
| --- | --- |
| `int getCredits()` | Supplies positive credit values for weighted QCA and attempted credits. |
| `String toString()` | Supplies readable module information for the transcript. |

`getCredits()` is an expected interface, not a verified method in the missing Module implementation. If the actual method requires permissions or uses a different signature, coordinate the corresponding changes to `Semester` and `Transcript`. Ensure module keys have consistent identity/equality behaviour for use in a map.

Neither `Programme.java` nor the custom `Module.java` is included in the integrated snapshot. Java's built-in `java.lang.Module` is not a substitute.

## 4. Member 3 – Grades, Progression and AwardClassification

### 4.1 Semester results and Grades

```java
Semester semester = new Semester("Semester 1", "2026/27", student);
semester.addModuleResult(module, 65.0);
double qca = semester.calculateSemesterQCA();
```

The example assumes an existing custom `Module` object. Results are stored in `Map<Module, Double>`, where the `Double` is a percentage from 0 to 100, not a `Grades` object. `addModuleResult` rejects null modules, null grades and invalid percentages. Reusing an equal module key replaces its existing result. `getModuleResults()` returns an unmodifiable map view.

The existing static `Grades` methods are used directly:

- `Grades.isValidPercentage(double)` – validates a result.
- `Grades.getQpv(double)` – converts a percentage to a quality point value.
- `Grades.getLetterGrade(double)` – supplies the transcript's letter grade.

Semester QCA is the sum of credits multiplied by QPV, divided by total credits. An empty semester returns 0.0. The transcript calculates cumulative QCA across its recorded results using the same weighting, rather than averaging semester QCAs.

### 4.2 Progression and award information

Construct and populate the transcript using the student's actual programme and official academic decisions:

```java
Transcript transcript = new Transcript(student, programme,
    progression, awardClassification);
transcript.addSemester(semester);
String report = transcript.generateTranscript(permissions);
```

`student` and `programme` must not be null. Progression and award objects may be null until available; they can later be supplied through `setProgression(Progression)` and `setAwardClassification(AwardClassification)`.

Existing interfaces in the group snapshot include:

- `new Progression(double qca, int failedModules)` with `getStatus()`, `getReason()` and `canProgress()`.
- `new AwardClassification(double finalQca)` with `getClassification()` and a formatted `toString()`.

**Required display fix:** `Transcript` currently calls `progression.toString()`, but the supplied `Progression` class does not override it. Agree either a meaningful override or a change to the transcript to call `getStatus()` and optionally `getReason()`. The existing award output contains QCA and classification, not qualification or completion date; agree how to supply those fields if required.

Earned credits remain pending agreed pass/compensation rules. Attempted credits are calculated from all recorded module results. Agree repeat/link-in and cumulative-credit rules before treating these totals as official records. Faculty-side code should supply validated results and decisions, not allow student-facing code to modify them.

## 5. Transcript and menu access

`generateTranscript(EnumSet<Permission>)` declares `throws Exception` and checks `readStudent`, `readProgramme` and `readModule`. `displaySemesterDetails(EnumSet<Permission>)` also declares `throws Exception` and passes permissions to the student's name getters.

| Menu option | Current status |
| --- | --- |
| 1. View Personal Details | Connected to protected Student getters. |
| 2. Update Personal Details | Permission-restricted; ownership and persistence pending. |
| 3. View Programme Information | Requires a populated transcript and real Programme implementation. |
| 4. View Registered Modules | Not implemented; requires registration data. |
| 5. Select Optional Modules | Not implemented; requires eligibility and registration logic. |
| 6. View Academic Transcript | Connected to transcript generation; requires populated records and missing dependencies. |
| 0. Log Out | Exits the menu loop; outer application must manage session lifecycle. |

## 6. Build and integration checks

Once the missing classes and interfaces have been supplied, run from the repository root:

```sh
mvn clean compile
```

For the temporary demonstration only, after successful compilation:

```sh
java -cp target/classes StudentRecordsSimulation
```

The demonstration uses a hardcoded sample student and account and passes a null transcript. It is not the final application startup and cannot demonstrate programme or transcript functionality.

Before merging, verify:

- [ ] All dependent callers use the updated Student signatures.
- [ ] Successful and unsuccessful authentication behave correctly.
- [ ] Student A cannot access or modify Student B's record.
- [ ] Unauthorized writes are rejected and permitted self-edits persist after restart.
- [ ] Programme details and registered/optional modules use the real group implementations.
- [ ] Semester and cumulative QCA agree with Grades and actual module credits.
- [ ] Transcript displays real progression status, award information and agreed credit totals.
- [ ] The complete repository compiles without temporary stand-ins and the final entry point works.

## 7. Integration status

This README is based on the supplied integrated archive. Full compilation against the real Programme and Module implementations has not been verified. No missing interfaces are presented as already implemented. The outstanding items above require coordination with the relevant group members before final submission.
