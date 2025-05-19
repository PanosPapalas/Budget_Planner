/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File_Management;

import Data_Models.Transaction_Model;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;



/**
 *
 * @author jack1
 */
class Transactions_List {
    private static final Map<YearMonth, List<Transaction_Model>> MAP = new HashMap<>();
    private static final ObservableList<Transaction_Model> masterList = FXCollections.observableArrayList();
    private static final SimpleBooleanProperty onChanged = new SimpleBooleanProperty(false);
    
    /*
    for the recurring transactions have will be on a seperate table that will only have that transaction the last time it was put in the normal
    transaction table and when the recurrance is supposed to happen (weekly, montly, yearly)
    */
    
    public static void initialize(){
        masterList.addListener((ListChangeListener<Transaction_Model>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(p -> {
                        YearMonth deptId = YearMonth.of(p.getDate().getYear(), p.getDate().getMonth());
                        List<Transaction_Model> deptList = MAP.get(deptId);
                        if (deptList != null) {
                            deptList.remove(p);
                            if (deptList.isEmpty()) {
                                MAP.remove(deptId);
                            }
                        }
                    });
                    onChanged.set(true);
                }
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(p -> {
                        YearMonth deptId = YearMonth.of(p.getDate().getYear(), p.getDate().getMonth());
                        MAP.computeIfAbsent(deptId, k -> FXCollections.observableArrayList()).add(p);
                    });
                    onChanged.set(true);
                }
            }
            onChanged.set(false);
        });
    }
    
    public static ObservableList<Transaction_Model> getMasterList(){
        return masterList;
    }
    
    public static SimpleBooleanProperty getOnChange(){
        return onChanged;
    }
    public static void DataBaseAccess(){
        
    }
    public static void addTransaction(Transaction_Model t){
        masterList.add(t);
    }
    
    public static void removeTransaction(Transaction_Model t){
        masterList.remove(t);
    }
    
    public static Map<YearMonth, List<Transaction_Model>> getAllTransactions(){
        return MAP;
    }
    
    public static List<Transaction_Model> getListFrom(YearMonth key){
        return MAP.getOrDefault(key, Collections.emptyList());
    }
}

/*
public static void addTransaction(Transaction_Model t){
        if(MAP.get(YearMonth.from(t.getDate())) == null){
            List<Transaction_Model> l = new ArrayList<>();
            l.add(t);
            MAP.put(YearMonth.from(t.getDate()), l);
        }else{
            MAP.get(YearMonth.of(t.getDate().getYear(), t.getDate().getMonth())).add(t);
        }
    }
    
    public static void removeTransaction(Transaction_Model t){
        if(MAP.get(YearMonth.of(t.getDate().getYear(), t.getDate().getMonth())).size() == 1){
            MAP.remove(YearMonth.of(t.getDate().getYear(), t.getDate().getMonth()));
        }else{
            List<Transaction_Model> l= MAP.get(YearMonth.of(t.getDate().getYear(), t.getDate().getMonth()));
            l.removeIf(item -> item.equals(t)); 
        }
    }

masterList.addListener((ListChangeListener<Person>) change -> {
    while (change.next()) {
        if (change.wasRemoved()) {
            for (Person p : change.getRemoved()) {
                int deptId = p.getDepartmentId();
                ObservableList<Person> deptList = departmentMap.get(deptId);
                if (deptList != null) {
                    deptList.remove(p);
                    if (deptList.isEmpty()) {
                        departmentMap.remove(deptId);
                    }
                }
            }
        }
        if (change.wasAdded()) {
            for (Person p : change.getAddedSubList()) {
                int deptId = p.getDepartmentId();
                departmentMap
                    .computeIfAbsent(deptId, k -> FXCollections.observableArrayList())
                    .add(p);
            }
        }
    }
});
Following is the list of values returned by various datatypes of java.sql.Type − i.e. getColumnType() gives these results.

Array: 2003
Big int: -5
Binary: -2
Bit: -7
Blob: 2004
Boolean: 16
Char: 1
Clob: 2005
Date: 91
Datalink:70
Decimal: 3
Distinct: 2001
Double: 8
Float: 6
Integer: 4
JavaObject: 2000
Long var char: -16
Nchar: -15
NClob: 2011
Varchar: 12
VarBinary: -3
Tiny int: -6
Time stamp with time zone: 2014
Timestamp: 93
Time: 92
Struct: 2002
SqlXml: 2009
Smallint: 5
Rowid: -8
Refcursor: 2012
Ref: 2006
Real: 7
Nvarchar: -9
Numeric: 2
Null: 0
Smallint: 5

You said:
in javafx i have a table that accepts data from a mySQLite data base how can i make it so that the columns are created dynamically and get their type by the type the columns are in the database. Can you help me? 
ChatGPT said:
Yes, I can help you with that. In JavaFX, you can dynamically create TableColumns based on the metadata of your SQLite query result. This involves:

Connecting to the database.

Executing a query to get the result set.

Reading metadata from the ResultSet.

Dynamically creating TableColumns.

Populating the TableView with rows.

Here's a step-by-step JavaFX example:

2. Java Code (Dynamic TableView Example)
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;

public class DynamicTableViewApp extends Application {
    private TableView<ObservableList<Object>> tableView = new TableView<>();

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Dynamic TableView from SQLite");
        stage.show();

        populateTableFromDatabase("jdbc:sqlite:your_database.db", "SELECT * FROM your_table");
    }

    private void populateTableFromDatabase(String dbUrl, String query) {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear previous columns and items
            tableView.getColumns().clear();
            tableView.getItems().clear();

            // Dynamically add columns
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i - 1;
                String columnName = metaData.getColumnName(i);

                TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);
                column.setCellValueFactory(
                    new Callback<CellDataFeatures<ObservableList<Object>, Object>, javafx.beans.value.ObservableValue<Object>>() {
                        public javafx.beans.value.ObservableValue<Object> call(CellDataFeatures<ObservableList<Object>, Object> param) {
                            return new javafx.beans.property.SimpleObjectProperty<>(param.getValue().get(columnIndex));
                        }
                    });

                tableView.getColumns().add(column);
            }

            // Add data rows
            while (rs.next()) {
                ObservableList<Object> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                tableView.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
Notes:
The column data types are inferred from ResultSet.getObject(i), which will return an appropriate Java type based on the SQL type (e.g., String, Integer, Double, etc.).

This approach assumes you want a general-purpose table and don’t need type-specific formatting or editing.

Would you like me to help you adapt this to an existing project or handle specific formatting (like dates, booleans, etc.)?

You said:
can you show me handling dates and booleans?
ChatGPT said:
Absolutely! When working with dates and booleans from SQLite in JavaFX, you need to detect the type of each column and apply formatting or conversion in the TableColumn setup.

Here's how to extend the example to handle DATE and BOOLEAN columns properly:

🔁 Updated Column Creation Logic with Type Handling
Replace the column creation loop in the previous example with this:

for (int i = 1; i <= columnCount; i++) {
    final int columnIndex = i - 1;
    String columnName = metaData.getColumnName(i);
    int columnType = metaData.getColumnType(i);

    TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);

    column.setCellValueFactory(param -> {
        Object value = param.getValue().get(columnIndex);

        // Handle BOOLEAN (assumes value is Integer or Boolean)
        if (columnType == Types.BOOLEAN || columnType == Types.BIT) {
            Boolean boolValue;
            if (value instanceof Boolean) {
                boolValue = (Boolean) value;
            } else if (value instanceof Number) {
                boolValue = ((Number) value).intValue() != 0;
            } else {
                boolValue = Boolean.parseBoolean(String.valueOf(value));
            }
            return new javafx.beans.property.SimpleObjectProperty<>(boolValue);
        }

        // Handle DATE/TIMESTAMP
        if (columnType == Types.DATE || columnType == Types.TIMESTAMP) {
            String formattedDate = "";
            if (value instanceof java.sql.Date || value instanceof java.sql.Timestamp) {
                java.time.LocalDateTime dateTime;
                if (value instanceof java.sql.Date) {
                    dateTime = ((java.sql.Date) value).toLocalDate().atStartOfDay();
                } else {
                    dateTime = ((java.sql.Timestamp) value).toLocalDateTime();
                }
                formattedDate = dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } else if (value != null) {
                formattedDate = value.toString(); // fallback
            }
            return new javafx.beans.property.SimpleObjectProperty<>(formattedDate);
        }

        // Default case
        return new javafx.beans.property.SimpleObjectProperty<>(value);
    });

    tableView.getColumns().add(column);
}
✅ SQLite Data Types Mapping
BOOLEAN: Often stored as INTEGER (0/1), so you need to cast and convert accordingly.

DATE/DATETIME: Typically stored as TEXT or INTEGER. If your column is DATETIME, consider parsing String or long timestamps manually if needed.

🧠 Tip: For Full Control, Use a Custom Cell Factory
For better UI (e.g., checkbox for boolean or calendar icon for date), use a cell factory:

// Checkbox for Boolean
column.setCellFactory(tc -> new CheckBoxTableCell<>());
Let me know if you want this enhanced with interactive editors (e.g., date pickers or boolean toggles).

You said:
how would i have a calendar icon for the dates?
ChatGPT said:
Great question! If you want a calendar icon next to your date values in a JavaFX TableView, there are a few ways to approach it. Here’s how you can do it with a custom cell factory that displays both:

a calendar icon, and

the formatted date string.

✅ Step-by-Step: Add Calendar Icon to Date Columns
🔧 1. Prepare the Calendar Icon
You can use:

A small image icon (e.g., PNG/SVG), or

A Unicode calendar emoji (🗓️), or

A FontAwesome / Glyph if using a library like FontAwesomeFX.

For simplicity, we’ll use a Unicode emoji in this example.

🧱 2. Custom Cell Factory for Date Columns
Assuming your column is already created, here's how to set the custom cell factory:

column.setCellFactory(col -> new TableCell<ObservableList<Object>, Object>() {
    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Format the date
            String formattedDate = "";
            if (item instanceof java.sql.Date || item instanceof java.sql.Timestamp) {
                java.time.LocalDateTime dateTime;
                if (item instanceof java.sql.Date) {
                    dateTime = ((java.sql.Date) item).toLocalDate().atStartOfDay();
                } else {
                    dateTime = ((java.sql.Timestamp) item).toLocalDateTime();
                }
                formattedDate = dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } else {
                formattedDate = item.toString();
            }

            // Create label with calendar emoji and date
            setText("🗓️ " + formattedDate);  // You could use an HBox with ImageView too
        }
    }
});
💡 Optional: Use an Image Icon Instead of Emoji
If you prefer an image icon (e.g., PNG file):

ImageView icon = new ImageView(new Image("calendar.png"));
icon.setFitWidth(16);
icon.setFitHeight(16);

Label label = new Label(formattedDate);
HBox hbox = new HBox(5, icon, label);
setGraphic(hbox);
Make sure the "calendar.png" is in your resources folder and correctly loaded.

✅ Final Integration
To apply this only for date columns, you can modify the populateTableFromDatabase method:

After detecting a DATE or TIMESTAMP column,

Use .setCellFactory(...) as shown above.

Would you like me to add editable support, like clicking the calendar icon to open a DatePicker?

You said:
can you show me?
ChatGPT said:
Absolutely! Here's how to add a clickable calendar icon in each table cell that opens a DatePicker for editing date values in a TableView.

✅ GOAL:
Show a calendar icon and a formatted date.

When clicked, show a JavaFX DatePicker to edit the value.

Save the new date back to the table's data model (ObservableList<Object>).

🧱 Full Example: Date Column with Editable Calendar
🔁 Custom Cell Factory with DatePicker
Add this to your populateTableFromDatabase method inside the loop, after creating the date column:

if (columnType == Types.DATE || columnType == Types.TIMESTAMP) {
    column.setCellFactory(col -> new TableCell<ObservableList<Object>, Object>() {
        private final DatePicker datePicker = new DatePicker();

        {
            // Handle commit on selection
            datePicker.setOnAction(event -> {
                LocalDate newDate = datePicker.getValue();
                if (newDate != null) {
                    // Update underlying data model
                    ObservableList<Object> row = getTableView().getItems().get(getIndex());
                    row.set(columnIndex, java.sql.Date.valueOf(newDate));
                    commitEdit(newDate);
                }
            });
        }

        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                // Show an HBox with calendar icon and date
                ImageView icon = new ImageView(new Image("calendar.png"));
                icon.setFitWidth(16);
                icon.setFitHeight(16);

                String formattedDate = "";
                if (item instanceof java.sql.Date || item instanceof java.sql.Timestamp) {
                    LocalDate localDate;
                    if (item instanceof java.sql.Date) {
                        localDate = ((java.sql.Date) item).toLocalDate();
                    } else {
                        localDate = ((java.sql.Timestamp) item).toLocalDateTime().toLocalDate();
                    }
                    formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    datePicker.setValue(localDate);
                } else {
                    formattedDate = item.toString();
                }

                Label label = new Label(formattedDate);
                HBox hbox = new HBox(5, icon, label);
                hbox.setOnMouseClicked(event -> {
                    // Replace cell content with DatePicker on click
                    setGraphic(datePicker);
                    setText(null);
                    datePicker.requestFocus();
                    datePicker.show();
                });

                setGraphic(hbox);
                setText(null);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (datePicker != null) {
                setGraphic(datePicker);
                setText(null);
                datePicker.requestFocus();
                datePicker.show();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            updateItem(getItem(), false);
        }
    });
}
🖼️ Don't forget the image
Place your calendar.png in the resources folder and load it like:

new Image(getClass().getResourceAsStream("/calendar.png"))
If you’re not using an image, you can just use a Unicode emoji:

Label label = new Label("🗓️ " + formattedDate);
🔁 Updates to Model
Notice how this line updates the underlying ObservableList<Object>:

row.set(columnIndex, java.sql.Date.valueOf(newDate));
This ensures that any changes are reflected immediately in the table’s data model. You could also trigger a database update here if needed.

✅ Summary:
Cell shows calendar icon + date.

Clicking opens a DatePicker for editing.

Changes are saved back to the row’s data model.

Would you like me to include saving the edited date back to SQLite as well, or keep it just in the UI for now?

You said:
show me about sending the edit back to SQLite
ChatGPT said:
Perfect — let's now handle saving the edited date back to your SQLite database after the user updates a date via the DatePicker.

✅ GOAL
When a user:

selects a new date from the DatePicker,

it updates the UI table, AND

it executes an UPDATE SQL statement to persist the change in SQLite.

📋 Requirements
To perform the update, you need:

A primary key column (like id) in your table to identify each row.

The name of the column being edited.

A method to run the UPDATE SQL.

🧱 1. Update Your Table to Include Row IDs
Let’s assume:

Your table has a column named id (INTEGER PRIMARY KEY).

You're querying like:

SELECT id, name, date_column FROM your_table
You will modify the code to:

Remember column names.

Use the id field to identify the record during updates.

🧱 2. Add SQLite Update Logic
Here’s a helper method that updates the date:

private void updateDateInDatabase(int rowId, String columnName, LocalDate newDate) {
    String updateSQL = "UPDATE your_table SET " + columnName + " = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:your_database.db");
         PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

        pstmt.setDate(1, java.sql.Date.valueOf(newDate));
        pstmt.setInt(2, rowId);

        pstmt.executeUpdate();
        System.out.println("Updated row " + rowId + " in column '" + columnName + "' to " + newDate);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
🧱 3. Modify the Date Cell Factory to Use This
Inside your custom cell factory (from previous step), after committing the new date:

datePicker.setOnAction(event -> {
    LocalDate newDate = datePicker.getValue();
    if (newDate != null) {
        ObservableList<Object> row = getTableView().getItems().get(getIndex());
        row.set(columnIndex, java.sql.Date.valueOf(newDate));
        commitEdit(newDate);

        // 🔁 Get row ID and send update to SQLite
        int id = ((Number) row.get(0)).intValue();  // Assuming ID is the first column
        String columnName = metaData.getColumnName(i);  // Get column name dynamically
        updateDateInDatabase(id, columnName, newDate);
    }
});
Make sure metaData is accessible from that scope. If it’s not, you can:

Capture the column name earlier in the loop.

Store it as a final String and pass it to the cell factory.

🔐 Security Tip:
Use PreparedStatement to prevent SQL injection, as shown.

✅ Final Result:
User selects a new date via calendar icon → DatePicker.

The UI is updated.

SQLite is updated in real-time.

Would you like a version of this wrapped as a reusable method or class for multiple columns or types?

You said:
yes, please!
ChatGPT said:
Great! Let’s now wrap this into a reusable method that:

Dynamically creates a date column with an editable calendar icon.

Automatically updates both the JavaFX TableView and the SQLite database when a date is changed.

Can be used with any table, as long as the column has a known name and there’s a unique id.

✅ 1. Utility Method: Create Editable Date Column
Here’s a reusable method you can drop into your controller or utility class:

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TableUtils {

    public static TableColumn<ObservableList<Object>, Object> createEditableDateColumn(
            String columnName,
            int columnIndex,
            String tableName,
            String dbUrl
    ) {
        TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnName);

        // Cell value factory: extract value from row
        column.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().get(columnIndex)));

        // Cell factory: render cell with calendar + editable DatePicker
        column.setCellFactory(col -> new TableCell<>() {
            private final DatePicker datePicker = new DatePicker();
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            {
                datePicker.setOnAction(event -> {
                    LocalDate newDate = datePicker.getValue();
                    if (newDate != null) {
                        ObservableList<Object> row = getTableView().getItems().get(getIndex());
                        row.set(columnIndex, java.sql.Date.valueOf(newDate));
                        commitEdit(newDate);

                        // Get ID from column 0 (assuming it's the primary key)
                        int id = ((Number) row.get(0)).intValue();
                        updateDateInDatabase(dbUrl, tableName, columnName, id, newDate);
                    }
                });
            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Format date
                    LocalDate localDate = null;
                    if (item instanceof java.sql.Date) {
                        localDate = ((java.sql.Date) item).toLocalDate();
                    } else if (item instanceof java.sql.Timestamp) {
                        localDate = ((java.sql.Timestamp) item).toLocalDateTime().toLocalDate();
                    } else if (item instanceof LocalDate) {
                        localDate = (LocalDate) item;
                    }

                    if (localDate != null) {
                        datePicker.setValue(localDate);
                        Label dateLabel = new Label("🗓️ " + formatter.format(localDate));
                        HBox hbox = new HBox(5, dateLabel);
                        hbox.setOnMouseClicked(e -> {
                            setGraphic(datePicker);
                            setText(null);
                            datePicker.requestFocus();
                            datePicker.show();
                        });
                        setGraphic(hbox);
                        setText(null);
                    } else {
                        setText(item.toString());
                        setGraphic(null);
                    }
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                setGraphic(datePicker);
                setText(null);
                datePicker.requestFocus();
                datePicker.show();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                updateItem(getItem(), false);
            }
        });

        return column;
    }

    private static void updateDateInDatabase(String dbUrl, String tableName, String columnName, int id, LocalDate newDate) {
        String sql = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(newDate));
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Updated " + tableName + ": set " + columnName + " = " + newDate + " for id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
✅ 2. How to Use It
In your populateTableFromDatabase() method, after identifying a DATE or TIMESTAMP column, just call:

TableColumn<ObservableList<Object>, Object> dateCol =
    TableUtils.createEditableDateColumn(columnName, columnIndex, "your_table", "jdbc:sqlite:your_database.db");
tableView.getColumns().add(dateCol);
Replace "your_table" and "jdbc:sqlite:..." with your actual values.

Make sure your query includes the id column as the first column so this line works:

int id = ((Number) row.get(0)).intValue();
✅ Summary
With this utility, you can:

Dynamically create editable date columns.

Display a calendar icon with the date.

Open a DatePicker on click.

Save changes back to SQLite.

Would you like a similar utility method for boolean columns with checkboxes that also updates the database?

You said:
No, but thank you for your help!!
ChatGPT said:
You're very welcome! 😊 I'm glad I could help — it sounds like you're building something great with JavaFX and SQLite.

If you ever need help with:

Sorting, filtering, or pagination in TableView

Other editable column types

Connecting your UI to a DAO or repository layer

Exporting to CSV or PDF

Feel free to reach out.

Good luck with your project, and happy coding! 💻📅✅




Attach

Search

Reason

Voice
No file chosenNo file chosen
ChatGPT can make mistakes. Check important info. See Cookie Preferences.

*/