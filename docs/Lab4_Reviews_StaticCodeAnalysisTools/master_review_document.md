# Technical Review

The main reason of the master review document is to provide a comprehensive overview of the project and the review process. It should include all the information about the project, the review process, the participants, the review objects, the reference documents, the checklist, and the additional notes.

Major goals behind this technical review:
- Early detection of defects to avoid costly fixes later
- Improving the quality of software artifact (code, documentation, etc.)
- Promoting team collaboration and knowledge sharing

## Review Information

- **Review Number:** 1
- **Project Name:** FocusFlow
- **Project Manager:** Tim Jauch
- **Quality Expert:** Max Trautwein

## Review Objects

- FocusFlow introductory text
- The functional system requirements and specification: https://github.com/dgrewe-hse/focusflow/blob/dev/docs/spec/spec.md
- Existing code base of the core entities so far: https://github.com/dgrewe-hse/focusflow/tree/dev/backend/src/main/java/de/hse/focusflow


## Reference Documents

- [FocusFlow Software Requirements Specification](spec.md)

## Checklist

- [x] Code Style and Formatting
- [x] Functionality and Logic
- [x] Error Handling (not implemented)
- [x] Documentation
- [x] Performance Considerations
- [x] Security Considerations (see findings)
- [x] Testing Coverage
- [x] Compliance with Standards

## Participating Reviewers and Roles

- Author: Prof. Grewe
- Reviewer: Ergün Bickici, Felix Hoffmann, Max Trautwein, Tim Jauch
- Moderator: Felix Hoffmann
- Note-Taker: Tim Jauch

## Review Decision

Rework required and follow-up review required with a re-inspection

Regarding the findings there are some key issues found that can not be ignored:
- Security concerns: Password stored and tested in plain text, weak password length policy, lack of role specification
- Requirements flaws: Missing or unclear information in [FocusFlow Software Requirements Specification](spec.md) (team lead rules, contradictions to intro text)
- Test coverage: Several tests only validate Lombok-generated getters/setters — no real logic.

After these issues have been reworked a follow-up review can be scheduled and a re-inspection can take place


## Date of Review

- **Date:** 05.04.2025

## Additional notes

No further findings

