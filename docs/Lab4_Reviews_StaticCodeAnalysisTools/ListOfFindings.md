# List of Findings

| No. | Review Object        | Finding Location | Description   | Checklist/Scenario   | Found By   | Severity Level | Comments   | Status                                      | Responsible Person   |
| --- | -------------------- | ---------------- | ------------- | -------------------- | ---------- | -------------- | ---------- | ------------------------------------------- | -------------------- |
| 1   | Introductory Text | 2. absatz | Contextual information | Documentation | Max | Minor  | - | Open | Grewe |
| 2   | Introductory Text | 4. absatz | may vs can | Documentation | Felix | Minor  | may might not be understood | Open | Grewe |
| 3   | Introductory Text | Summarised 2. | meaning of Review | Documentation | Tim | Minor  | Review meaning too vague | Open | Grewe |
| 4   | spec.md  | 2.2 | No Description how to become Team Leader. |  Functionality and Logic | Max | Major  | - | Open | Grewe |
| 5   | spec.md | 2.2   | No Info what Teames a Team Lead can Manage |  Functionality and Logic | Max | Major  | - | Open | Grewe |
| 6   | spec.md  | 2.2  | regular User is seperate from Team user | Documentation | Max | Minor  | - | Open | Grewe |
| 7   | spec.md | 3.2 .4 | If only the assignee can alter the ticket may prevent effective working  |  Functionality and Logic | max | Major  | - | Open | Grewe |
| 8   | spec.md | 3.2 .1 | Wiederspruch zu Introductory Text: Zuweisung an Team oder User VS belibig viele User | Documentation | max | Major  | - | Open | Grewe |
| 9   | spec.md | 3.4  | Task completion rates Seem more comlicated then requiered for a simple system |  Functionality and Logic | max | Minor  | - | Open | Grewe |
| 10   | spec.md | 4.2  | "10-12 characters length" It would be bad / insecure to limit at 12 Chars  |  Security Considerations | Max | Major  | - | Open | Grewe |
| 11   | spec.md | 4.3      | Can wie really provide / is that really requiered  99.9% Uptime (Thats 8.77 hours per year in downtime)   |  Performance Considerations | Max | Minor  | - | Open | Grewe |
| 12   | spec.md | 4.3 .3 | How long should we keep those Backups | Documentation | max | Minor  | - | Open | Grewe |
| 13   | spec.md  | 5.1 .3      | "Responsive design for all screen sizes" Thats way to Broad, that would include smart watches and smaller stuff |  Functionality and Logic | Max | Major | - | Open | Grewe |
| 14   |  spec.md | 5.2 .2   | "Weekend maintenance windows" Unclear whats expected. will cause issues with 99.9 Uptime Req | Performance Considerations | Max | Major  | - | Open | Grewe |
| 15   | spec.md | 5.4 .4      | "No deletion of in-progress tasks" Could hinder effectiv colaberation |  Functionality and Logic | max | Minor  | - | Open | Grewe |
| 16   | spec.md | 5.4 .2    | "Prevention of concurrent modifications" Unclear what the Intet is. Are we expected to serialize alle Actions &rarr; has Performence / Cuncurrent User Implications OR should we just pervent errors that can occur with "concurrent modifications" | Documentation | Max | Major  | - | Open | Grewe |
| 17   | spec.md | 6.2 .1       | Inconsistant Phrasing to 5.3.1 | Documentation | Max | Minor  | - | Open | Grewe |
| 18   | /model/User.java | L20 & L25  | Limiting name Size will cause issues with some users and long names | Error Handling | Max | Major  | - | Open | Grewe |
| 19   | /model/User.java | L35      | Limiting Lenght is Bad Especially in the DB Where it will be hashed With extra data such as Salt. It WILL BE Longer |  Security Considerations | Max | Major  | - | Open | Grewe |
| 20   | /model/User.java | L53     | Role is never Specified & Unclear Use | Documentation | Max | Minor  | - | Open | Grewe |
| 21   | /model/....java | Type used / Many   | Check if HashSet is the best data type. |  Performance Considerations | Max | Minor  | Hashing tends to be slower for enumeration | Open | Grewe |
| 22   | /model/....java | On each model class     | "lombok Setter and Getter" Look Nice but offer not ooptions for Custom Logic. Not everthing should be settable. |  Functionality and Logic | Max | Minor  | - | Open | Grewe |
| 23   | model/Notification.java & Base Entity | L23 & L25 L29 | LocalDateTime will cause issues with multible time zones |  Error Handling | Max | Major  | - | Open | Grewe |
| 24   | /model/User.java also respectiv Test | L37      | In contarast to the Spec the PW is saved in Plain Text |  Security Considerations | Max | Major  | - | Open | Grewe |
| 25   | All Tests | general       | There is no logic, we only test lombok. that seems useless |  Testing Coverage | Max | Minor  | - | Open | Grewe |
| 26   | UserTest.java | testUserCreation() method       | Password is stored and compared in plain text in test (no hashing/encoding logic tested) |  Testing Coverage, Security Considerations | Tim | Major  | - | Open | Grewe |
