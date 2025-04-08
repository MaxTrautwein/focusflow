# Exercise 4 - Review & Static Code Analysis Tools

## Exercise 4.1 (12 Points): Conduct a review

Reviewed documents:

[x] FocusFlow introductory text

[x] The functional system requirements and specification: https://github.com/dgrewe-hse/focusflow/blob/dev/docs/spec/spec.md

[x] Existing code base of the core entities so far: https://github.com/dgrewe-hse/focusflow/tree/dev/backend/src/main/java/de/hse/focusflow

Choosen type of review: [Technical Review (Formal review)](master_review_document.md)

List of findings from the mentioned reviewed documents: [List of findings](ListOfFindings.md)

## Exercise 4.2 (5 Points): Retrospective

Reflect on the review

- Reviews are a helpful method to ensure the goal of the software project is met. Helps detecting issues, strengstens knowledge, makes sure the keep eyes on the actual use of the product an not to overengineer.

- In this case however we did not view the review as meaningful, because the issues found will have no impact on our own project.

- Peer reviews are necessary and implemented in our project and this review process hardened the necessity of it.

## Exercise 4.3 (10 Points): Static Code Analysis Tools for FocusFlow

### Installing maven-checkstyle-plugin:

- 1.  Adding the maven-checkstyle-plugin to the pom.xml, put this in the <plugins>

  Add this code:

    <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.0</version>
        <configuration>
            <configLocation>src/main/resources/focusFlowCodeStyleChecker.xml</configLocation>
        </configuration>
    </plugin>

- 2.  Add a rule set in a .xml file to the project path: src/main/resources

      Add this code to the file:

      <?xml version="1.0"?>

            <!DOCTYPE module PUBLIC
            "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
            "https://checkstyle.org/dtds/configuration_1_3.dtd">

            <module name="Checker">

                {...Define rule set here..}

            </module>

      </module>

- 3.  Execute this command "mvn checkstyle:check" in the project path "..\backend\focusflow".
      If the the code has no errors the build will be successful.
      If the code has errors it will fail the build and u can see teh error messages directly in the console.

### Installing maven-pmd-plugin:

- 1. Adding the maven-pmd-plugin to the pom.xml, put this in the <plugins>:

     Add this code:

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-pmd-plugin</artifactId>
        <version>3.26.0</version>
        <configuration>
            <rulesets>
                <ruleset>src/main/resources/focusFlowPmdRuleSet.xml</ruleset>
            </rulesets>
            <targetJdk>21</targetJdk>
            <failOnViolation>true</failOnViolation>
        </configuration>
    </plugin>

- 2. Add a rule set in a .xml file to the project path: src/main/resources

     Add this code to the file:

    <?xml version="1.0"?>

    <ruleset name="focusflow PMD Rules">

        <description>
            Custom rules for FocusFlow source code
        </description>

        {...Define rule set here..}

    </ruleset>

- 3. Execute this command "mvn compile pmd:check" in the project path "..\backend\focusflow".
     If the the code has no errors the build will be successful.
     If the code has errors it will fail the build and u will find the error description in the pmd.xml file project path: "..\target\pmd.xml".

## Evaluation

    Checkstyle and PMD are valuable tools for improving code quality in Java projects. Checkstyle helps enforce coding standards by checking for formatting issues, naming conventions, and code structure. This leads to more consistent and readable code across the team. PMD analyzes the source code to find common bugs, unused variables, and potential performance issues. It can catch problems early before they become harder to fix later.

    Both tools can slow down the development process cause they cause more work, but if plugin's like prettier are used there shouldn't be often a failure in the code structure. So in the end they are more use full for the development of our project than they slow us down. Especially when they prevent buggs.
