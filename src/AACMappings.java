import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AACMappings {
    AssociativeArray<String, AACCategory> categories;
    AACCategory homePage;
    AACCategory currentCategory;
    File configFile;

    public AACMappings(String filename) {
        this.categories = new AssociativeArray<String, AACCategory>();
        this.homePage = new AACCategory("");
        this.currentCategory = this.homePage;
        
        this.configFile = new File(filename);
        try (Scanner input = new Scanner(new FileReader(this.configFile))) {
            String categoryImage = null;
            String categoryText = null;
            String text = null;
            String imagePath;
            String currentLine;
            String[] currentLineTokens;
            while (input.hasNextLine()) {
                currentLine = input.nextLine();
                currentLineTokens = currentLine.split(" ", 2);
                
                if (currentLine.startsWith(">")) {
                    imagePath = currentLineTokens[0].substring(1);
                    text = currentLineTokens[1];
                    try {
                        this.categories.get(categoryImage).addItem(imagePath, text);
                    } catch (KeyNotFoundException knfe) {
                        knfe.printStackTrace();
                    }
                } else {
                    imagePath = currentLineTokens[0];
                    categoryText = currentLineTokens[1];
                    try {
                        this.categories.set(imagePath, new AACCategory(imagePath));
                    } catch (NullKeyException nke) {
                        nke.printStackTrace();
                    }
                    this.homePage.addItem(imagePath, categoryText);
                    categoryImage = imagePath;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    public void addMapping(String imageLoc, String text) {
        if (this.currentCategory.equals(this.homePage)) {
            AACCategory newCategory = new AACCategory(text);
            try {
                this.categories.set(imageLoc, newCategory);
            } catch (NullKeyException nke) {
                nke.printStackTrace();
            }
            this.homePage.addItem(imageLoc, text);
            this.currentCategory = newCategory;
        } else {
            this.currentCategory.addItem(imageLoc, text);
        }
    }

    public String getCurrentCategory() {
        return this.currentCategory.getCategory();
    }

    public String[] getImageLocs() {
        return this.currentCategory.getImages();
    }

    public String getText(String imageLoc) throws Exception {
        String result = this.currentCategory.getText(imageLoc);
        if (result.equals("Not Found")) {
            throw new Exception("Text not found for the image location.");
        }

        if (this.currentCategory.equals(this.homePage)) {
            try {
                this.currentCategory = this.categories.get(imageLoc);
            } catch (KeyNotFoundException knfe) {
                throw new Exception("Category not found for the image location.");
            }
        }
        return result;
    }

    public boolean isCategory(String imageLoc) {
        return this.homePage.hasImage(imageLoc);
    }

    public void resetToHomePage() {
        this.currentCategory = this.homePage;
    }

    public void saveMappingsToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            AACCategory currentCategory;
            for (KVPair<String, AACCategory> categoryName : this.categories) {
                writer.write(categoryName.getKey() + " " + homePage.getText(categoryName.getKey()) + "\n");
                currentCategory = categoryName.getValue();
                for (KVPair<String, String> catImagepath : currentCategory.mappings) {
                    writer.write(">" + catImagepath.getKey() + " " + catImagepath.getValue() + "\n");
                }
            }
        } catch (IOException ioe) {
            System.err.println("IO Error");
        }
    }
}
