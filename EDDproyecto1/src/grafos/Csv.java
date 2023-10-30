package grafos;
import java.util.*; 
import java.io.File;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import grafos.User;
import grafos.UsersRelation;

// la clase con la procesamos los archivos de texto y lo convertimos en una estructura en Java , los usuarios en objetos y lo guardamos en ese array 
public class Csv {
    FileReader file;
    String filePath;
    BufferedReader reader;
    User[] usersArr = {};
    Integer[] usersIDList = {};
    UsersRelation[] usersRelationsArr = {};
    String status = "success";
    String message = "";
    
    public void FileSelection(String file_type) { // Creamos el jfilechooser  
        File selectedFile;
        JFileChooser selectFile;
        selectFile = new JFileChooser();
        Assets assets = new Assets(); // La creamos para tener funciones que se puedan repetir.
        Csv csv;
        
        selectFile.showOpenDialog(null);
        selectedFile = selectFile.getSelectedFile(); //ejecutamos la funcion que abrela ventana del archivo
        
        if(selectedFile == null) {
            //show dialog modal
            this.message = "Debe seleccionar un archivo";
            this.status = "wrong"; // Despliega el mensaje de error y el estatus.
        } else {
            String fileExtension = assets.getFileNameExtension(selectedFile.getName());// llamamos una funcion para obtener una extension del archivo. y verificamos si es txt o no.
            if(!fileExtension.equals("txt")) {
                //show dialog modal
                this.message = "Debe seleccionar un archivo con la extensión .txt";
                this.status = "wrong";
                return;
            }
            this.processFile(selectedFile.getAbsolutePath(), file_type);// procesamos el archivo, lo leemos
        }
    }
    
    public void processFile(String filePath, String type) { //convertimos el archivo de txt y lo convertimos es array (una lista de elemento ( {1,2,2,2,6,4,6,8}) Dependiendo como organices los datos pueden ser una pila o una cola.
        try{ // 
            this.filePath = filePath;// apuntamos las 
            this.file = new FileReader(filePath);// leemos el archivo
            if(this.file.ready()) {
                this.reader = new BufferedReader(file);// Buffered lee el texto de un archivo en formato de bytes y lo convierte en texto.
                String fileLine;
                while((fileLine = this.reader.readLine()) != null) {
                    boolean checkCommas = checkCommas(fileLine);//chequeamos las comas 
                    String columns[];
                    
                    if(!checkCommas) {
                        this.message = "El archivo CSV debe estar delimitado por comas (,)";
                        this.status = "wrong";
                    }
                    columns = fileLine.split(",");
                    if(type == "users") {
                        processUserLine(columns); //Convertimos la linea en una lista
                    }
                    if(type == "users_relations") {
                        processUsersRelationLine(columns);// Aqui deoendiendo de lo que estamos revisando
                    } // lo analizamos para convertirlo en un objeto en un item de nuestro array
                    
                    
                }
            } else {
                this.message = "El archivo no está listo para ser leído";
                this.status = "wrong";
            }
        } catch(Exception e) {
            System.out.println("Error:"+e.getMessage());
            this.message = "Error inesperado.";
            this.status = "wrong";
        }
        System.out.println(Arrays.toString(this.usersArr));
        System.out.println(Arrays.toString(this.usersRelationsArr));
    }
    
    private void processUsersRelationLine(String[] columns) {
        if(columns.length != 3 ) {
            this.message = "Cada fila debe tener tres columnas (ni mas ni menos).";
            this.status = "wrong";
            return;
        }
        if(!isInteger(columns[0])) {
            this.message = "Los datos de la primera columna deben ser de tipo entero";  /// hace validaciones y crear un objeto de la clase usuario.
            this.status = "wrong";
            return;
        }
        if(!isInteger(columns[1])) {
            this.message = "Los datos de la segunda columna deben ser de tipo entero";
            this.status = "wrong";
            return;
        }
        if(!isInteger(columns[2])) {
            this.message = "Los datos de la tercera columna deben ser de tipo entero";
            this.status = "wrong";
            return;
        }
        UsersRelation newUsersRelation = new UsersRelation(); // Creamos el objeto
        newUsersRelation.User1ID = Integer.parseInt(columns[0]); // Creamos el 1ID
        newUsersRelation.User2ID = Integer.parseInt(columns[1]);// Creamos el 2ID
        newUsersRelation.RelationTime = Integer.parseInt(columns[2]);// Creamos el tiempo de relacion
        addNewUsersRelation(newUsersRelation); // agregamos el nuevo usuario a la nueva lista de relaciones
    }

    private void addNewUsersRelation(UsersRelation newUsersRelation) {
        int n = this.usersRelationsArr.length;  
        if(newUsersRelation.User1ID.equals(newUsersRelation.User2ID)) {
            this.message = "Un usuario no puede estar relacionado con sigo mismo. ID: "+newUsersRelation.User1ID.toString();
            this.status = "wrong";
            return;
        }
        if(!Arrays.stream(this.usersArr).anyMatch(x -> x.ID.equals(newUsersRelation.User1ID))){
            this.message = "El usuario de ID: "+newUsersRelation.User1ID.toString()+" no existe en la lista de usuarios.";
            this.status = "wrong";
            return;
        }
        if(!Arrays.stream(this.usersArr).anyMatch(x -> x.ID.equals(newUsersRelation.User2ID))){
            this.message = "El usuario de ID: "+newUsersRelation.User2ID.toString()+" no existe en la lista de usuarios.";
            this.status = "wrong";
            return;
        }
        if(Arrays.stream(this.usersRelationsArr).anyMatch(
                x -> x.User1ID.equals(newUsersRelation.User1ID) && x.User2ID.equals(newUsersRelation.User2ID))
        ) {
            this.message = "La relación entre los usuarios de ID : "+newUsersRelation.User1ID.toString()+" y ID: "+newUsersRelation.User2ID.toString()+" está repetida en la lista de relaciones.";
            this.status = "wrong";
            return;
        }
        if(Arrays.stream(this.usersRelationsArr).anyMatch(
                x -> x.User2ID.equals(newUsersRelation.User1ID) && x.User1ID.equals(newUsersRelation.User2ID)
        )) {
            this.message = "La relación entre los usuarios de ID : "+newUsersRelation.User2ID.toString()+" y ID: "+newUsersRelation.User1ID.toString()+" está repetida en la lista de relaciones.";
            this.status = "wrong";
            return;
        }
        
        UsersRelation newUsersRelationsArr[] = new UsersRelation[n+1]; // Creamos una lista con lo anterior que ya tenias
        for(int i = 0; i<n; i++) {  // 
            newUsersRelationsArr[i] = this.usersRelationsArr[i]; // RECORREMOS LA LISTA VIEJA PARA HACER LA LISTA NUEVA 
        }  
        
        for(int u = 0; u < this.usersArr.length; u++) {
            if(this.usersArr[u].ID.equals(newUsersRelation.User1ID)) {
                newUsersRelation.User1 = this.usersArr[u];                   // Y A;ANADIR LOS ID 
            }
            if(this.usersArr[u].ID.equals(newUsersRelation.User2ID)) { //PREGUNTAMOS LOS ID
                newUsersRelation.User2 = this.usersArr[u];
            }
        }
        newUsersRelationsArr[n] = newUsersRelation;      ///RECORREMOS LA LISTA Y BUSCAMOS LOS USUARIOS QUE TIENEN LOS ID QUE PUSIMOS
        this.usersRelationsArr = newUsersRelationsArr; // A;ADE EL NUEVO USUARIO A LA LISTA
    }
    
    private void processUserLine(String[] columns) {
        if(columns.length != 2 ) {
            this.message = "Cada fila debe tener dos columnas (ni mas ni menos).";
            this.status = "wrong";
            return;
        }
        if(!isInteger(columns[0])) { // VERIFICAMOS QUE LA COLUMNA 1 SEA NUMERO ENTERO
            this.message = "Los datos de la primera columna deben ser de tipo entero";
            this.status = "wrong";
            return;
        }
        User newUser = new User(); // VERIFICAMOS LAS COLUMNAS, TIENE QUE TENER DOS COLUMNAS POR EL ID Y EL USER
        newUser.ID = Integer.parseInt(columns[0]);// ASIGNAMOS EL ID DEL NUEVO USUARIO QUE ESTAMOS CREANDO, LO QUE TIENE LA COLUMNA 1 SE LO ASIGNAMOS AL NUEVO USUARIO.
        newUser.Username = columns[1];// ASIGNAMOS EL NOMBRE DE USUARIO
        addNewUser(newUser); // LO HACEMOS CON TODAS LAS LINEAS TXT
    }
    
    private boolean checkCommas(String Textline) {
        int dotIndex = Textline.lastIndexOf(',');
        return (dotIndex == -1) ? false : true; //CHEQUE LA COMA
    }
    
    public static boolean isInteger(String strNum) { //PREGUNTA SI ES ENTERO
        if (strNum == null) {
            return false;
        }
        try {
            int result = Integer.parseInt(strNum);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public void addNewUser(User newUser) {  // a;adimos el usuario a la lista de usuarios
        int n = this.usersArr.length; //El tama;o del array mas el que vamos agregar 
        Integer[] newUserIDSList = new Integer[this.usersIDList.length+1];// creamos nueva lista
        if(Arrays.stream(this.usersIDList).anyMatch(x -> x == newUser.ID)){ //Verificamos si ya esta agregaado 
            this.message = "El usuario de ID: "+newUser.ID.toString()+" está repetido en la lista de usuarios.";
            this.status = "wrong";
            return; // VALIDAMOS 
            //AddNewuser en una lista de usuario y La NewUserId donde tenemos los ID
        }
        User newUsersArr[] = new User[n+1];  //
        for(int i = 0; i<n; i++) {  
            newUsersArr[i] = this.usersArr[i];  
            newUserIDSList[i] = this.usersIDList[i];//RECOREMOS PARA A;ADIR A LOS USUARIOS ANTERIORES 
        }  
        newUsersArr[n] = newUser;  
        newUserIDSList[n] = newUser.ID;  
        this.usersArr = newUsersArr;// AÑADIMOS USERSARR AL NEWUSERSARR 9 NUEVO ARRAY CON EL NUEVO USUARIO   
        this.usersIDList = newUserIDSList; // AÑADIMOS EL USUARIO NUEVO NUEVA DE LOS ID CON EL AGREGADO
    }  
}
