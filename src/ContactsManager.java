
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.*;


public class ContactsManager {

    final static String dir = "data";
    final static String fileName = "contacts.txt";

    static Scanner scan = new Scanner(System.in);

    //   string list - contacts
    static List<String> contacts = new ArrayList<>();
    //   object list  - contactObjects
    static List<Contact> contactsObjects = new ArrayList<>();
    static Path path = Paths.get(dir, fileName);


    //INITIALIZE FILE & DIRECTORY
    public static void init() {

        Contact user1 = new Contact("bryan", "2034825751");
        Contact user2 = new Contact("eduardo","2103949062");
        Contact user3 = new Contact("andrew", "6949095454");
        Contact user4 = new Contact("melinda", "2107659832");

        contactsObjects.add(user1);
        contactsObjects.add(user2);
        contactsObjects.add(user3);
        contactsObjects.add(user4);
        //if dir/file does not exist, create it... if unable - throw exception
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(Paths.get(dir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //VIEW CONTACTS
//    string list = contacts
//    objectlist = contactObjects
    public static void viewContacts() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(dir,fileName));

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        for (Contact contact : contactsObjects) {
//            System.out.println(contact.getName() + " | " + contact.getTelephone());
//        }
    }



    //ADD CONTACT
//    string list = contacts
//    objectlist = contactObjects
    public static void addContact() {
        //        ADD contact method
        System.out.println("What is the name of the contact you'd like to add?");
        String name = scan.nextLine();

        System.out.println("What is the best number to contact them?");
        String telephone = scan.nextLine();

        Contact newContact = new Contact(name, telephone);

//        String contactInfo = newContact.getName() + " | " + newContact.getTelephone();

        contactsObjects.add(newContact);
//        contacts.add(contactInfo);

        String contactInfo = name + "-" + telephone;

        try {
            Files.write(path, Arrays.asList(contactInfo), StandardOpenOption.APPEND);
//            Files.write(path, contacts);
//            Files.write(path, instructors, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //SEARCH FOR CONTACT
//    string list = contacts
//    objectlist = contactObjects
    public static void searchContacts() {
        String result = "";
        System.out.println("What is the name of the contact you are looking for?");
        String searchQuerey = scan.nextLine();
        for (Contact contact : contactsObjects) {
            if (searchQuerey.equalsIgnoreCase(contact.getName()) || searchQuerey.equals(contact.getTelephone())) {
               result = contact.getName() + "-" + contact.getTelephone();
                break;
            } else {
                result = "sorry, that contact does not exist";
            }
        }
        System.out.println(result);
    }

    //DELETE A CONTACT

    public static void deleteContact(){
        boolean isThere = true;
        System.out.println("Enter the name to delete:");
        String nameDelete = scan.nextLine();
        List<String> tempList = new ArrayList<>();
        int counter = 0;
        try{
            for(String line : Files.readAllLines(path)){
                String[] eachLine = line.split("-");
                if(eachLine[0].equalsIgnoreCase(nameDelete)){
                    counter++;
                }
                //editList.add(line);
            }

            if(counter == 1){
                for(String line : Files.readAllLines(path)){
                    String[] eachLine = line.split("-");
                    if(eachLine[0].equalsIgnoreCase(nameDelete)){
                        continue;
                    }
                    tempList.add(line);
                }
            }else if(counter > 1){
                System.out.println("There were multiple entries with the same name.");
                System.out.println("Please enter a phone number to delete:");
                String phoneDelete = scan.nextLine();
//                boolean isThere = checkPhone(phoneDelete); // what does this do>?

                if(isThere){
                    for(String line : Files.readAllLines(path)){
                        String[] eachLine = line.split("-");
                        if(eachLine[1].equalsIgnoreCase(phoneDelete)){
                            continue;
                        }
                        tempList.add(line);
                    }
                }else{
                    System.out.println("Contact not in file.");
                }

            }
            if(tempList.size() > 1){
                Files.write(path, tempList);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    //    string list = contacts
//    objectlist = contactObjects
    public static void reWrite() {
        //        write to file, if unable, throw exception
        try {
            for(Contact contact : contactsObjects){
                String contactInfo = contact.getName() + "-" + contact.getTelephone();
                Files.write(path, Arrays.asList(contactInfo), StandardOpenOption.APPEND);
            }
//            Files.write(path, contacts);
//            Files.write(path, instructors, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
         boolean wantsToContinue = true;
        init();




//        *************************************
        while (wantsToContinue) {
            System.out.println("1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5):");

            String UserSelection = scan.nextLine();


            switch (UserSelection) {
                case "1":
                    System.out.println("View Contacts\n~~~~~~~~~~~~~");
                    viewContacts();
                    break;
                case "2":
                    System.out.println("Add a new Contact\n~~~~~~~~~~~~~");
                    addContact();
                    break;
                case "3":
                    System.out.println("Search for Contacts\n~~~~~~~~~~~~~");
                    searchContacts();
                    break;
                case "4":
                    System.out.println("Delete a Contact\n~~~~~~~~~~~~~");
                    deleteContact();
                    break;
                case "5":
                    System.out.println("Saving...");
                    System.out.println("Exiting...");
                    System.out.println("Thank you for using Contacts Manager!");
//                    reWrite();
                    wantsToContinue = false;
                    break;
            }
        }
    }
}
