import java.util.Scanner;
import javax.swing.JFileChooser;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JournalApplication
{
    public void menu(Scanner scanner)
    {
        File journalFile = null;
        while (true)
        {
            System.out.println("\nJournalio");
            System.out.println("1. Create new journal.");
            System.out.println("2. Write to journal.");
            System.out.println("3. Open journal text file.");
            System.out.println("4. Delete journal file.");
            System.out.println("5. Exit.");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt())
            {
                int menuChoice = scanner.nextInt();
                scanner.nextLine();

                switch (menuChoice)
                {
                    case 1:
                    journalFile = createJournal(scanner);
                    break;

                    case 2:
                    if (journalFile != null)
                    {
                        writeToJournal(scanner, journalFile);
                    }
                    else
                    {
                        System.out.println("No journal has been created yet.");
                    }
                    break;

                    case 3:
                    openFile(journalFile);
                    break;

                    case 4:
                    deleteFile(scanner, journalFile);
                    break;

                    case 5:
                    return;

                    default:
                    System.out.println("Invalid input. Make sure you input a valid integer.");
                }
            }
            else
            {
                System.out.println("Invalid input. Make sure you input a valid integer.");
                scanner.next();
            }
        }
    }

    public File createJournal(Scanner scanner)
    {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle("Select a Folder");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = directoryChooser.showOpenDialog(null);
        
        if (result != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Aborted.");
            return null;
        }

        File selectedDirectory = directoryChooser.getSelectedFile();

        System.out.print("Choose a file name (including file type, i.e. '.txt'): ");
        String fileName = scanner.nextLine();

        File journalFile = new File(selectedDirectory, fileName);

        try
        {
            if (journalFile.createNewFile())
            {
                System.out.println("File '" + journalFile.getName() + "' has been successfully created at: " + journalFile.getAbsolutePath());
            }
            else
            {
                System.out.println("File already exists at path: " + journalFile.getAbsolutePath());
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return journalFile;
    }
        
    public File writeToJournal(Scanner scanner, File journalFile)
    {
        System.out.print("Enter text to add to your journal: ");
        String text = scanner.nextLine();

        try
        {
            FileWriter fileWriter = new FileWriter(journalFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("Text has been added to your journal.");
        }

        catch (IOException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        return journalFile;
    }

    public void openFile(File journalFile)
    {
        if (!journalFile.exists())
        {
            System.out.println("File does not exist!");
            return;
        }

        try
        {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(journalFile);
        }

        catch (IOException e)
        {
            System.out.println("Error occured.");
            e.printStackTrace();
        }
    }

    public void deleteFile(Scanner scanner, File journalFile)
    {
        System.out.println("Are you sure you want to delete this file (Yes, No)? ");
        String yesOrNo = scanner.nextLine();

        if (yesOrNo.equalsIgnoreCase("yes"))
        {
            boolean isDeleted = journalFile.delete();

            if (isDeleted)
            {
                System.out.println("File " + journalFile.getName() + ", at path " + journalFile.getAbsolutePath() + "was deleted.");
            }
            else
            {
                System.out.println("Failed to delete this file. It may not exist or is in use.");
            }
        }
        else
        {
            return;
        }
    }
        
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        JournalApplication obj = new JournalApplication();
        obj.menu(scanner);
        scanner.close();
    }
}
