# CareCode Middleware Management Tool

## Title

CareCode Middleware Management Tool

## Description

The CareCode Middleware Management Tool is an open-source utility designed to streamline the monitoring and control of middleware applications used in healthcare environments. Specifically tailored to work alongside CareCode Open HMIS, this tool provides an intuitive interface for managing multiple middleware applications, including TCP/IP clients and servers. It ensures seamless operation by offering features for process monitoring, status updates, and automatic restarts, enhancing the reliability and robustness of healthcare systems.

Built with Java SE, this tool offers user-friendly controls for starting, stopping, and restarting middleware processes. In addition, the tool includes real-time monitoring, heartbeat checks, and automatic recovery options, making it an essential utility for healthcare IT management.

## Features

- **Process Monitoring**: Track the status of each middleware application with real-time updates.
- **Process Control**: Start, stop, or restart applications from a single interface.
- **Heartbeat Check**: Ensures each middleware application is active and responsive.
- **Automatic Restart**: Automatically restarts applications that unexpectedly stop, maintaining continuous operation.
- **Integration Ready**: Seamlessly integrates with CareCode Open HMIS, enhancing its operational resilience.
- **User-Friendly Interface**: Simple controls for end-users, designed for ease of use in healthcare settings.

## Installation

1. Clone the repository.
2. Import the project into NetBeans (or any Java IDE).
3. Set up a `config.properties` file to specify paths for each middleware application.
4. Run the application to begin monitoring and controlling middleware processes.

## Usage

Upon launching the application, you will be presented with a graphical interface displaying each middleware application's status. Users can interact with buttons to start, stop, or restart individual applications. The application continuously monitors each process, providing automatic restarts for stopped applications.

## Credits

This tool was developed as a part of the CareCode Open HMIS initiative, spearheaded by Dr. M. H. B. Ariyaratne. We would like to extend our thanks to the entire open-source community and contributors to the CareCode Open HMIS project for their insights and support.

Special thanks to OpenAI's ChatGPT for technical assistance in drafting the foundational structure and providing ongoing development guidance for this middleware management tool.

## License

This project is licensed under the **GNU Affero General Public License (AGPL)**. For more details, see the `LICENSE.md` file in the repository.
