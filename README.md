# Mock Server for Web Scraping

This project is a "mock" server created to simulate HTTP responses for web scraping tests. It provides data in a controlled and predictable manner, ensuring that your tests are consistent and independent of external websites.

---

### **Motivation**

Testing web scrapers directly on live websites can be problematic. Pages can change, network speed can vary, and access may be restricted by security policies. Using this mock server solves these issues, allowing the development and testing of your web scraper in a stable and isolated environment.

---

### **How It Works**

The mock server hosts HTML versions of a specific website. However, the original content of these documents has been modified. All text has been replaced with random content to preserve potential **copyrights**. The HTML structure, classes, and IDs, which are essential for web scraping, have been kept intact to ensure the scraper functions as if it were interacting with the original site.

---

### **Credits**

The HTML documents used in this project are based on the structure of the following website:

> **[WitchCultTranslations](https://witchculttranslation.com/)**

**Disclaimer**: This project uses a modified version of the site's HTML structure solely for testing purposes. The content has been altered to ensure no copyrighted material is used or distributed. This project is not affiliated with or endorsed by the original website.

---

### **Web Scraping Project Repository**

This server is a companion component to the main web scraping project. For more information, including how to use this mock server in your tests, please visit the main repository:

[**https://github.com/Playerrl-ux/Multi-Thread-WebScrapping**](https://github.com/Playerrl-ux/Multi-Thread-WebScrapping)

---

### **How to Run the Project (Spring Boot)**

This project is built with **Spring Boot**, **Maven**, and **Java 21**. Follow these steps to get it up and running:

1.  **Prerequisites**: Make sure you have **Java 21** and **Maven** installed on your system.
2.  **Clone the repository**:
    ```bash
    git clone [https://github.com/your-username/this-project-mockserver.git](https://github.com/your-username/this-project-mockserver.git)
    cd this-project-mockserver
    ```
3.  **Build the project**: Use Maven to build the project and download all dependencies.
    ```bash
    mvn clean install
    ```
4.  **Run the application**: Execute the Spring Boot application from the command line.
    ```bash
    mvn spring-boot:run
    ```

The server will start on port `8080` by default. You can now configure your web scraper to point to `http://localhost:8080` to begin your tests.
