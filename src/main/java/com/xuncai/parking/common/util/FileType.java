package com.xuncai.parking.common.util;

/** 
 * 文件类型枚取 
 */  
public enum FileType {  
      
    /** 
     * JEPG. 
     */  
    JPEG("FFD8FF"),  
    /** 
     *  JPG. 
     */  
   
    JPG("FFD8FF"),
      
    /** 
     * PNG. 
     */  
    PNG("89504E47"),  
      
    /** 
     * GIF. 
     */  
    GIF("47494638"),  
    /** 
     * MOBILEPROVISION. 
     */  
    MOBILEPROVISION("30823B12"),
    /** 
     * P12. 
     */  
    P12("30820C89"),//30820CC9
    /** 
     * TIFF. 
     */  
    TIFF("49492A00"),  
      
    /** 
     * Windows Bitmap. 
     */  
    BMP("424D"),  
      
    /** 
     * CAD. 
     */  
    DWG("41433130"),  
      
    /** 
     * Adobe Photoshop. 
     */  
    PSD("38425053"),  
      
    /** 
     * Rich Text Format. 
     */  
    RTF("7B5C727466"),  
      
    /** 
     * XML. 
     */  
    XML("3C3F786D6C"),  
      
    /** 
     * HTML. 
     */  
    HTML("68746D6C3E"),  
      
    /** 
     * Email [thorough only]. 
     */  
    EML("44656C69766572792D646174653A"),  
      
    /** 
     * Outlook Express. 
     */  
    DBX("CFAD12FEC5FD746F"),  
      
    /** 
     * Outlook (pst). 
     */  
    PST("2142444E"),  
      
    /** 
     * MS Word/Excel. 
     */  
    XLS_DOC("D0CF11E0"),  
    /** 
     * MS Excel. 
     */  
    XLS("D0CF11E0"),  
    /** 
     * MS Word 
     */  
    DOC("D0CF11E0"),    
    /** 
     * MS PPT. 
     */  
    PPT("D0CF11E0"),   
    /** 
    * XLSX Archive. 
    */  
    XLSX("504B0304"),  
    /** 
     * DOCX Archive. 
     */  
    DOCX("504B0304"),  
    /** 
     * PPTX Archive. 
     */  
    PPTX("504B0304"), 
    /** 
     * TXT Archive. 
     */  
    TXT("32303136"), 
    /** 
     * MS Access. 
     */  
    MDB("5374616E64617264204A"),  
      
    /** 
     * WordPerfect. 
     */  
    WPD("FF575043"),  
      
    /** 
     * Postscript. 
     */  
    EPS("252150532D41646F6265"),  
      
    /** 
     * Adobe Acrobat. 
     */  
    PDF("255044462D312E"),  
      
    /** 
     * Quicken. 
     */  
    QDF("AC9EBD8F"),  
      
    /** 
     * Windows Password. 
     */  
    PWL("E3828596"),  
      
    /** 
     * RAR Archive. 
     */  
    RAR("52617221"),  
      
    /** 
     * Wave. 
     */  
    WAV("57415645"),  
      
    /** 
     * AVI. 
     */  
    AVI("41564920"),  
      
    /** 
     * Real Audio. 
     */  
    RAM("2E7261FD"),  
      
    /** 
     * Real Media. 
     */  
    RM("2E524D46"),  
      
    /** 
     * MPEG (mpg). 
     */  
    MPG("000001BA"),  
      
    /** 
     * Quicktime. 
     */  
    MOV("6D6F6F76"),  
      
    /** 
     * Windows Media. 
     */  
    ASF("3026B2758E66CF11"),  
    
    MP4("00000020667479706"),
    FLV("464C5601050000000"),
    /** 
     * MIDI. 
     */  
    MID("4D546864");  
   
      
    private String value = "";  
      
    /** 
     * Constructor. 
     *
     *  @param
     */  
    private FileType(String value) {  
        this.value = value;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public void setValue(String value) {  
        this.value = value;  
    }  
}  