# OpenCart Test Automation Framework

A self-built hybrid test automation framework for the OpenCart e-commerce application, using Selenium WebDriver and Java.

> Personal learning project built to move from manual testing into automation.

## Tech Stack

| Area | Tool |
|------|------|
| Language | Java |
| Browser automation | Selenium WebDriver |
| Test runner | TestNG |
| Design pattern | Page Object Model (POM) |
| Data-driven testing | Apache POI (Excel) |
| Reporting | Extent Reports |
| Logging | Log4j2 |
| Build tool | Maven |
| Remote execution | Selenium Grid on Docker |

## Features

- Page Object Model for maintainable, reusable page classes
- Data-driven tests that read test data from Excel using Apache POI
- Extent HTML reports with pass/fail status
- Automatic screenshots on test failure
- Log4j2 logging for debugging
- Reusable utilities (Excel reader, data providers, Extent report manager)
- Cross-browser execution through TestNG suite XML parameters

## Project Structure

```
OpencartV121
OpencartV121
├── src/test/java
│   ├── pageObjects        # AccountRegistrationPage, BasePage, HomePage, LoginPage, MyAccountPage
│   ├── testBase           # BaseClass (setup, teardown, logger)
│   ├── testCases          # TC001_AccountRegistrationTest, TC002_LoginTest, TC003_LoginDDT, TC004_Search
│   └── utilities          # DataProviders, ExcelUtility, ExtentReportManager
├── src/test/resources     # config.properties, log4j2.xml
├── testData               # Excel test data
├── screenshots            # Failure screenshots
├── reports                # Extent HTML reports
├── logs                   # Log files
├── master.xml             # Main regression suite
├── grouping.xml           # Grouped tests
├── crossbrowsertesting.xml # Cross-browser execution
├── grid-docker.xml        # Remote execution on Selenium Grid
├── docker-compose.yaml    # Selenium Grid setup (Docker)
├── run.bat                # Batch file to run the tests
└── pom.xml

```

## Prerequisites

- JDK 21.0.1
- Apache Maven 3.10.0
- Chrome / Firefox / Edge browser
- IDE: Eclipse

## How to Run

1. Clone the repo:
```bash
   git clone https://github.com/MD-YOUSUFF/OpencartV121.git
   cd OpencartV121
```
2. Update `src/test/resources/config.properties` with the application URL and the existing user's credentials.

### Run locally

- **From Eclipse:** right-click `master.xml` → **Run As → TestNG Suite**
- **Grouped tests:** right-click `grouping.xml` → **Run As → TestNG Suite**
- **Cross-browser:** right-click `crossbrowsertesting.xml` → **Run As → TestNG Suite**
- **Command line:**
```bash
  mvn test
```
- **Batch file (Windows):** double-click `run.bat`

### Run on Selenium Grid (Docker)

1. Start the Grid:
```bash
   docker-compose -f docker-compose.yaml up -d
```
2. Run `grid-docker.xml` as a TestNG Suite (or use `mvn test -DsuiteXmlFile=grid-docker.xml`).
3. Stop the Grid when done:
```bash
   docker-compose -f docker-compose.yaml down
```

## Reports and Logs

- **Extent report:** `reports/` folder (open the HTML file in a browser)
- **Screenshots:** `screenshots/` folder (captured on failure)
- **Logs:** `logs/` folder

## Test Scenarios Covered

- New user account registration
- Login with an existing user (credentials from config.properties)
- Login with valid and invalid credentials
- Data-driven login using Excel

## Author

**MD Yousuff**
QA / Test Analyst, Bangalore, India
GitHub: [MD-YOUSUFF](https://github.com/MD-YOUSUFF)
