<a id="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]  [![Unlicense License][license-shield]][license-url]  [![Last Commit][last-commit-shield]][last-commit-url]

<div align="center">

  <h1 align="center">EVENTORIUM E2E TESTS</h1>

  <p align="center">
    End-to-end tests for the Eventorium event planning platform.
    <br />
    <a href="https://github.com/lazarnagulov/ts-project-event-planner-siit-2024-team-13/issues/new?labels=bug">Report Bug</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#-about-the-project">About The Project</a></li>
    <li><a href="#-prerequisites">Prerequisites</a></li>
    <li><a href="#-built-with">Built With</a></li>
    <li><a href="#-running-the-tests">Running the Tests</a></li>
    <li><a href="#-test-scenarios">Test Scenarios</a></li>
    <li><a href="#-related-repositories">Related Repositories</a></li>
  </ol>
</details>

---

## 📋 About The Project

This repository contains end-to-end (E2E) tests for the **Eventorium** platform using [Selenium](https://www.selenium.dev/). These tests are designed to verify core workflows and user interactions across the full stack, ensuring platform reliability and usability.
Happy planning with Eventorium! 🎉
<br/>

## 🔧 Built With

This project is powered by the following technologies:

[![Java][Java-img]][Java-url]  
[![Maven][Maven-img]][Maven-url]  
[![Selenium][Selenium-img]][Selenium-url]  
[![ChromeDriver][ChromeDriver-img]][ChromeDriver-url]

<br/>

## ⚙️ Prerequisites

Make sure you have the following installed before running the tests:

- [Java](https://www.oracle.com/java/) (JDK 17+ recommended)
- [Google Chrome](https://www.google.com/chrome/)
- [ChromeDriver](https://chromedriver.chromium.org/) matching your browser version (place it in the root directory)
- [Maven](https://maven.apache.org/)

<br/>

## 🧪 Running the Tests

To execute the test suite, make sure both the frontend and backend applications are running. Then simply run:

```bash
mvn test
```

If you're on Windows, the same command applies in cmd or PowerShell.
Tests will open a Chrome browser window and simulate a real user experience through various scenarios such as login, 
event planning, and management workflows.

<br/>

## 📌 Test Scenarios

The following key end-to-end scenarios are covered:

### 🗓️ Event Creation & Planning
- ✅ Create a new event with valid details
- ✅ Prevent creation with invalid (past) dates
- ✅ Add valid agenda activities
- ✅ Handle invalid agenda time ranges
- ✅ Remove invalid activities and finalize agenda
- ✅ Ensure event visibility to users
- ✅ Export guest list to PDF

### 💰 Budget Planning & Management
- ✅ Add and remove budget categories
- ✅ Search for service/product suggestions by category and price
- ✅ Add one or multiple items to the budget planner
- ✅ Update planned item amounts
- ✅ Delete items from the planner
- ✅ Mark items as *purchased*
- ✅ Reserve services with time slots (directly from planner or details page)
- ✅ Add purchases directly from product details

### 🔍 Event Search & Filtering

- ✅ Search by event name returns matching results
- ✅ Search by name with no matches returns no events
- ✅ Search with empty query returns all visible events
- ✅ Filter by combination of filter parameters returns correct results
- ✅ Filter with no matching criteria returns no events
- ✅ Combined search and filter returns correct and consistent results


## 🔗 Related Repositories
These tests interact with the following components of the Eventorium platform. Ensure both are up and running before testing:

🌐 Backend: [Eventorium Backend](https://github.com/kzi-nastava/iss-project-event-planner-siit-2024-team-13)

💻 Frontend: [Eventorium Frontend](https://github.com/kzi-nastava/iks-project-event-planner-siit-2024-team-13)

<br/>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[Java-img]: https://img.shields.io/badge/Java-17+-red?logo=java&logoColor=white
[Java-url]: https://www.oracle.com/java/
[Maven-img]: https://img.shields.io/badge/Maven-3-blue?logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org/
[Selenium-img]: https://img.shields.io/badge/Test-Selenium-43B02A?logo=selenium&logoColor=white
[Selenium-url]: https://www.selenium.dev/
[ChromeDriver-img]: https://img.shields.io/badge/ChromeDriver-automated--browser--testing-yellow?logo=googlechrome&logoColor=white
[ChromeDriver-url]: https://chromedriver.chromium.org/
[contributors-shield]: https://img.shields.io/github/contributors/lazarnagulov/ts-project-event-planner-siit-2024-team-13.svg?style=for-the-badge
[contributors-url]: https://github.com/lazarnagulov/ts-project-event-planner-siit-2024-team-13/graphs/contributors
[license-shield]: https://img.shields.io/github/license/lazarnagulov/ts-project-event-planner-siit-2024-team-13.svg?style=for-the-badge
[license-url]: https://github.com/lazarnagulov/ts-project-event-planner-siit-2024-team-13/blob/master/LICENSE.txt
[last-commit-shield]: https://img.shields.io/github/last-commit/lazarnagulov/ts-project-event-planner-siit-2024-team-13?branch=main&style=for-the-badge
[last-commit-url]: https://github.com/lazarnagulov/ts-project-event-planner-siit-2024-team-13/commits/main
