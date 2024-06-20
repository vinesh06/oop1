public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryUI libraryUI = new LibraryUI(library);
        libraryUI.showMainMenu();
    }
}
