# FGO-VCE (Fate Grand Order VerCode Extractor)

## 📖 Overview
FGO-VCE is a Java-based tool designed to extract and handle verification codes for Fate Grand Order. This tool provides a robust and improved implementation for managing FGO verification codes.

## 🛠️ Technical Architecture

### Technology Stack
- **Language**: Java (JDK 21)
- **Build Tool**: Maven
- **Main Dependencies**:
  - Jackson Core (2.9.6) - JSON processing
  - Jackson Annotations (2.9.6) - JSON annotations
  - Jackson Databind (2.9.6) - JSON object mapping
  - Picocli (4.7.6) - Command-line interface

### Project Structure
```
FGO-VCE/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── isaac/
│                   ├── Main.java
│                   └── commands/
│                       └── CheckVerCode.java
├── pom.xml
└── README.md
```

## ⚙️ Installation and Setup

### Prerequisites
- Java JDK 21
- Maven
- IntelliJ IDEA (Recommended IDE)

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/O-Isaac/FGO-VCE.git
   ```
2. Navigate to the project directory:
   ```bash
   cd FGO-VCE
   ```
3. Build the project using Maven:
   ```bash
   mvn clean package
   ```

## 🚀 Usage

The application uses a command-line interface built with Picocli. The main functionality is accessed through the `CheckVerCode` command.

### Command Structure
```bash
java -jar FGO-VCE.jar [options]
```

## 🔧 Technical Details

### Main Components

1. **Main Class (`Main.java`)**
   - Entry point of the application
   - Handles command-line argument processing
   - Integrates with Picocli for CLI functionality

2. **CheckVerCode Command**
   - Core functionality for verification code processing
   - Implements command-line interface options
   - Handles verification code extraction and validation

### Build Configuration

The project uses Maven for build automation and dependency management. Key build features:

- Automatic dependency management
- JAR packaging with dependencies
- Custom build cleanup process
- UTF-8 encoding enforcement

## 📦 Build Output

The build process generates a single JAR file with all dependencies included:
- Location: `target/FGO-VCE-1.0-SNAPSHOT-jar-with-dependencies.jar`
- The standard JAR without dependencies is automatically removed during build

## 🔍 Dependencies

### Core Dependencies
```xml
<dependencies>
    <!-- Jackson for JSON Processing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.9.6</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.9.6</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.9.6</version>
    </dependency>
    
    <!-- Command Line Interface -->
    <dependency>
        <groupId>info.picocli</groupId>
        <artifactId>picocli</artifactId>
        <version>4.7.6</version>
    </dependency>
</dependencies>
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests or create issues for bugs and feature requests.

## 📄 License

This project is open source and available under the MIT License.