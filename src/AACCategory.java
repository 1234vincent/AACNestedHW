public class AACCategory {
  
  /**
   * Fields
   */
  String name;
  AssociativeArray<String, String> mappings;
   
  /**
   * Constructor
   * Creates a new empty category with the given name.
   * @param name
   */
  public AACCategory(String name) {
    this.name = name;
    this.mappings = new AssociativeArray<String, String>();
  } // AACCategory(String)

  /**
   * Method
   * Adds the mapping of the imageLoc to the text of the category.
   * @param imageLoc
   * @param text
   */
  public void addItem(String imageLoc, String text) {
    try {
      this.mappings.set(imageLoc, text);
    } catch (NullKeyException nke) {
      System.err.println("Please enter a valid image location.");
    }
  } // class addItem(String, String)

  /**
   * Method
   * Returns the name of the category.
   * @return this.name
   */
  public String getCategory() {
    return this.name;
  } // getCategory()

  /**
   * Method
   * Returns an array of all the images in the category.
   * @return result of toString() method
   */
  public String[] getImages() {
    String images = "";
    for (KVPair<String, String> pair : this.mappings) {
      images += pair.getKey() + "\n";
    } // for
    
    return images.split("\n");
  } // getImages()

  /**
   * Method
   * Returns the text associated with the given image loc in this category.
   * @param imageLoc
   * @return filepath of the image or "Not Found"
   */
  public String getText(String imageLoc) {
    String result;
    try {
      result = this.mappings.get(imageLoc);
    } catch (KeyNotFoundException kne) {
      return "Not Found.";
    } // try catch

    return result;
  } // getText(String)

  /**
   * Method
   * Determines if the provided images is stored in the category.
   * @param imageLoc
   * @return result of hasKey(imageLoc)
   */
  public boolean hasImage(String imageLoc) {
    return this.mappings.hasKey(imageLoc);
  } // class hasImage(String)
} // class AACCategory
