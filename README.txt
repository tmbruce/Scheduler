HELLO EVALUATOR:

========== SECTION A:Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

Getting the user's language is handled in the SignInController file on line 116 with the getLanguage() function. A switch function is used to test the language against the available languages (French, Spanish) and English as default. This function gets the users language which is then passed to the switch statement and either translateFrench() or the translateSpanish() functions are called to translate the sign in screen to the user's language. Language translation for error messages are handled individually in the signInHandler() function. 

========== SECTION B: Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.

This is handled in the customerController file, accessible from the customer button on the left side of the main screen. The ability to create, update, and delete are handled by their respective buttons in that controller class. An interface is used to pass user information to the controller for "created by" insertion into database. As a secondary option, a customer's status can be updated to inactive, to keep the customer's information stored in the database and excluded from the active customers. When creating a customer, the country must be selected first, which pulls the associated cities and populates the drop down. 

========== SECTION C: Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.

The ability to create appointments is handled with the "Create Appointment" button on the mainScreenController class. After an appointment has been created, pushing the refresh button will show the appointment on the calendar. Each appointment on the calendar has an event handler attached to it to handle the ability to edit the appointment. In order to edit the appointment, simply click on the appointment and the edit appointment controller window will appear. This window allows the user to edit the details of the appointment.

========== SECTION D: Provide the ability to view the calendar by month and by week.

Toggle group accesses the weekly / monthly views. Appointments are added with the create appointment button. Editing appointments are handled by clicking on the appointment on the calendar to launch the edit appointment controller. If an appointment is added, edited, or deleted, the refresh button must be pressed to populate those changes on the visual representation of the calendar.

========== SECTION E: Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.

This is handled in the TimeShift class. Dates and times are inserted into the database in the UTC timezone. Dates are created in the application in the users locale, then converted with the LocalToUTC() function. Times are inserted into the database in UTC. On retrieval, date time formats are parsed, then converted to the user's local date time format with the UTCtoLocal() function in the TimeShift class. This adjusts for time zone changes and daylight savings time.

========== SECTION F: Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
•   scheduling an appointment outside business hours
•   scheduling overlapping appointments
•   entering nonexistent or invalid customer data
•   entering an incorrect username and password

Exception controls for appointments are handled in the Appointment class under the section commented with Validation Methods. Username and password validations are handled in the signInController class.

========== SECTION G: Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.

One of the two required lambda expressions is in the MainController class at line 383. Its purpose is to add an event handler to each appointment displayed on the calendar. The event handler, when clicked, launches the edit appointment controller.

The other required lambda expression is in the ReportsController class at line 109. Its purpose is to iterate over the user list array and populate the drop down with the names of all the users.

========== SECTION H:  Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.

This is handled in the MainController class starting at line 81.

========== SECTION I: Provide the ability to generate each  of the following reports:
•   number of appointment types by month
•   the schedule for each consultant
•   one additional report of your choice

This is handled in the ReportsController class, as accessed from the main screen controller with the reports button. The last report identifies the number of active/inactive users/customers in the system.

========== SECTION J: Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.

This functionality is handled in the signInController class beginning at line 281. The application will generate a text file if it does not exist in the application's main folder. The text file is labeled "accessLog.txt"
