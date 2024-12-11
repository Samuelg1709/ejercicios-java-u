 import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorGestorTareas {

    @FXML
    private TableView<Tarea> tableViewTareas;

    @FXML
    private TableColumn<Tarea, String> columnaNombre;

    @FXML
    private TableColumn<Tarea, String> columnaDescripcion;

    @FXML
    private TableColumn<Tarea, Boolean> columnaEstado;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoDescripcion;

    private ObservableList<Tarea> listaTareas;
    private GestorBD gestorBD;

    public void initialize() {
        gestorBD = new GestorBD(); 
        listaTareas = FXCollections.observableArrayList(); 

        
        columnaNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        columnaDescripcion.setCellValueFactory(data -> data.getValue().descripcionProperty());
        columnaEstado.setCellValueFactory(data -> data.getValue().estadoProperty());

        
        cargarTareas();
    }

    private void cargarTareas() {
        listaTareas.clear(); 
        listaTareas.addAll(gestorBD.obtenerTareas()); 
        tableViewTareas.setItems(listaTareas); 
    }

    @FXML
    void agregarTarea(ActionEvent event) {
        String nombre = campoNombre.getText();
        String descripcion = campoDescripcion.getText();

    
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios");
            return;
        }

       
        gestorBD.agregarTarea(nombre, descripcion, false);
        cargarTareas(); 

      
        campoNombre.clear();
        campoDescripcion.clear();
    }

    @FXML
    void eliminarTarea(ActionEvent event) {
        Tarea tareaSeleccionada = tableViewTareas.getSelectionModel().getSelectedItem();

        
        if (tareaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una tarea para eliminar");
            return;
        }
        gestorBD.eliminarTarea(tareaSeleccionada.getId());
        cargarTareas(); 
    }

    @FXML
    void completarTarea(ActionEvent event) {
        Tarea tareaSeleccionada = tableViewTareas.getSelectionModel().getSelectedItem();

        
        if (tareaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una tarea para completar");
            return;
        }

        
        gestorBD.completarTarea(tareaSeleccionada.getId());
        cargarTareas(); 
    }

   
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
