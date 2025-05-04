import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
// CONTINUED - FULL POSTER RENDERING IMPLEMENTATIONS
class InformativePosterFrame extends PosterFrame {
    private final String header;
    private final String[] titles;
    private final String[] descriptions;
    public InformativePosterFrame(String header, String[] titles, String[] descriptions, int colorChoice)//PARAMETRIZED CONS
     {
        super("Informative Poster", colorChoice);
        this.header = header;
        this.titles = titles;
        this.descriptions = descriptions;
    }
    @Override
    protected JPanel createPosterPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;// from Graphics to Graphics2D
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //method of Graphics 2d jisme rendering limits smoothness badhayega of shapes and other components
                g2.setColor(palette[5]); //Fixed Background
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setFont(new Font("Serif", Font.BOLD, 48));//48 is the size in points
                FontMetrics fm = g2.getFontMetrics(); //HADLW 
                int headerX = (A4_WIDTH - fm.stringWidth(header)) / 2;//x coordinate for starting HEADER
                g2.setColor(palette[0]);
                  drawCenteredWrappedText(g2, header, A4_WIDTH/2, 80, A4_WIDTH - 100, 60);

                int startY = 150;//draw starts
                int boxHeight = 205; 
                int horizontalPadding = 50; //spacing
                int contentWidth = A4_WIDTH - 2 * horizontalPadding;//constant,Image + Text
                int textBoxWidth = (contentWidth - 140) / 2;//varies -> accn to text

                for (int i = 0; i < 4; i++)//box
                 {
                    int boxY = startY + i *(boxHeight + 20); //starting coordinate
                    boolean left = i % 2 == 0; // left right formation

                    int textX = left ? horizontalPadding : horizontalPadding + textBoxWidth + 20 + 120;
                    //box=left -> horizontal padding,right -> 2nd condtion
                    int imgX = left ? horizontalPadding + textBoxWidth + 20 : horizontalPadding; //X coordinate of img1,right -> horizontal padding
                    //TextBox
                    g2.setColor(palette[1]);
                    g2.fillRoundRect(textX, boxY, textBoxWidth, boxHeight, 25, 25);//25 is the roundness of the box
                    g2.setColor(palette[0]);
                    g2.drawRoundRect(textX, boxY, textBoxWidth, boxHeight, 25, 25);//Border
                    //Text
                    g2.setFont(new Font("SansSerif", Font.BOLD, 18));
                    g2.setColor(palette[3]);
                    int titleHeight = drawWrappedText(g2, titles[i], textX + 15, boxY + 30, textBoxWidth - 30, 20);
                    //Description
                    g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                    g2.setColor(palette[0]);
                    drawWrappedText(g2, descriptions[i], textX + 15, boxY + 90, textBoxWidth - 30, 20);//Passing parameters(30 means down of title)
                    //Image Box
                    g2.setColor(palette[3]);
                    g2.fillRoundRect(imgX, boxY, 120, 120, 15, 15);
                    g2.setColor(palette[0]);
                    g2.drawRoundRect(imgX, boxY, 120, 120, 15, 15);
                    g2.drawString("Image", imgX + 40, boxY + 65);
                }
            }
                    private void drawCenteredWrappedText(Graphics2D g2, String text, int centerX, int startY, int maxWidth, int lineHeight) {
            FontMetrics fm = g2.getFontMetrics();
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder();
            int y = startY;
            
            for (String word : words) {
                if (fm.stringWidth(line + word + " ") > maxWidth) {
                    int lineWidth = fm.stringWidth(line.toString());
                    g2.drawString(line.toString(), centerX - lineWidth/2, y);
                    line = new StringBuilder();
                    y += lineHeight;
                }
                line.append(word).append(" ");
            }
            if (line.length() > 0) {
                int lineWidth = fm.stringWidth(line.toString());
                g2.drawString(line.toString(), centerX - lineWidth/2, y);
            }
                    }
            private int drawWrappedText(Graphics2D g2, String text, int x, int y, int width, int lineHeight) {
                FontMetrics fm = g2.getFontMetrics();
                String[] words = text.split(" ");//Spliting words
                StringBuilder line = new StringBuilder(); //Line generation
                int linedrawn = 1;
                for (String word : words) {
                    if (fm.stringWidth(line + word + " ") > width) //line breaking check
                     {
                        g2.drawString(line.toString(), x, y);
                        line = new StringBuilder(word + " ");
                        y += lineHeight;//aligns y pos down
                    } else {
                        line.append(word).append(" "); //add
                    }
                }
                g2.drawString(line.toString(), x, y);
                return linedrawn*lineHeight;
            }
        };
    }
}
class ConceptMapPosterFrame extends PosterFrame {
    private final String centerIdea;
    private final String[] connections;
    private final String headerTitle;

    public ConceptMapPosterFrame(String headerTitle, String centerIdea, String[] connections, int colorChoice) {
        super("Concept Map Poster", colorChoice);
        this.headerTitle = headerTitle;
        this.centerIdea = centerIdea;
        this.connections = connections;
    }

    @Override
    protected JPanel createPosterPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g; 
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(palette[5]);
                g2.fillRect(0, 0, getWidth(), getHeight());

                int centerX = A4_WIDTH / 2;//Center Alignment
                int centerY = A4_HEIGHT / 2;
                int ovalWidth = 200, ovalHeight = 100;

                // Header Title
                if (headerTitle != null && !headerTitle.trim().isEmpty()) //Checks if Header is preswent or not
                {
                    g2.setFont(new Font("Serif", Font.BOLD, 36));
                    drawCenteredText(g2, headerTitle, centerX, 60);
                }

                // Central Idea (Oval)
               g2.setColor(palette[2]);
               g2.fillOval(centerX - ovalWidth / 2, centerY - ovalHeight / 2, ovalWidth, ovalHeight);

// 2. Set font and color for text
g2.setColor(palette[0]);;
g2.setFont(new Font("SansSerif", Font.BOLD, 14));

// 3. Get text dimensions of Oval
FontMetrics fm = g2.getFontMetrics();//HADLW
String[] lines = centerIdea.split("\n"); // Split if multiline
int totalTextHeight = fm.getHeight() * lines.length;

// 4. Draw each line centered
int yPos = centerY - (totalTextHeight / 2) + fm.getAscent(); // Muliple lines are centered one below another 

for (String line : lines) {
    int textWidth = fm.stringWidth(line);
    int xPos = centerX - (textWidth / 2); // (x,y) centers the text inside box
    g2.drawString(line, xPos, yPos);//Text inside oval
    yPos += fm.getHeight(); // Move to next line
}
                // Connections
                int radius = 250;//Distance from center to boxes
                int boxW = 200, boxH = 100;
                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));

                for (int i = 0; i < connections.length; i++)//Iterates angle -> 360/6 
                {
                    double angle = 2 * Math.PI * i / connections.length;
                    int boxX = (int) (centerX + radius * Math.cos(angle) - boxW / 2);//X coordinate for each box
                    int boxY = (int) (centerY + radius * Math.sin(angle) - boxH / 2);//Y coordinate for each box

                    // Line from oval edge to box center
                    double dx = Math.cos(angle);
                    double dy = Math.sin(angle);
                    int startX = (int) (centerX + (ovalWidth / 2) * dx);// b +bw/2. b +bh/2 -> Box centered at 
                    int startY = (int) (centerY + (ovalHeight / 2) * dy);
                    g2.drawLine(startX, startY, boxX + boxW / 2, boxY + boxH / 2);
                           // Box  size
                    g2.setColor(palette[1]);
                    g2.fillRoundRect(boxX, boxY, boxW, boxH, 15, 15);
                    g2.setColor(Color.BLACK);
                    
                    // Adjusted text positioning for larger box
                    int textMargin = 15;  // Increased margin for better spacing
                    drawWrappedText(g2, connections[i], 
                                  boxX + textMargin, 
                                  boxY + textMargin + fm.getAscent(), 
                                  boxW - (textMargin * 1), 
                                  20,  // Increased line height
                                  true);
                }
            }

            private void drawCenteredText(Graphics2D g2, String text, int x, int y)//Header name 
             { //text consists of Header name
                FontMetrics fm = g2.getFontMetrics();//HADLW
                int width = fm.stringWidth(text);//TextWidth obtained
                g2.setColor(palette[0]);
                g2.drawString(text, x - width / 2, y);
            }

            private void drawWrappedText(Graphics2D g2, String text, int x, int y, int maxWidth, int lineHeight, boolean centerAlign) {
               //x-> bx+textm, y-> by+textm + fm.a,text consists of Connections[i]
                FontMetrics fm = g2.getFontMetrics();
                String[] words = text.split(" ");
                StringBuilder line = new StringBuilder();
                int drawY = y;
                for (String word : words) {
                    if (fm.stringWidth(line + word + " ") > maxWidth) {
                        drawLineAligned(g2, line.toString(), x, drawY, maxWidth, centerAlign);
                        drawY += lineHeight;
                        line = new StringBuilder();
                    }
                    line.append(word).append(" ");
                }
                if (!line.toString().isEmpty())//single or last text/
                 {
                    drawLineAligned(g2, line.toString(), x, drawY, maxWidth, centerAlign);
                }
            }
            private void drawLineAligned(Graphics2D g2, String line, int x, int y, int maxWidth, boolean center) {
                FontMetrics fm = g2.getFontMetrics();
                int lineWidth = fm.stringWidth(line);
                //maxwidth -> boxw - textm
                int drawX = center ? x + (maxWidth - lineWidth) / 2 : x;
                g2.drawString(line, drawX, y);
            }/////////////////////////////////////
        };
    }
}
class StepByStepPosterFrame extends PosterFrame {
    private final String title;
    private final String[] steps;

    public StepByStepPosterFrame(String title, String[] steps, int colorChoice) {
        super("Step-by-Step Poster", colorChoice);
        this.title = title;
        this.steps = steps;
    }

    @Override
    protected JPanel createPosterPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(palette[5]);
                g2.fillRect(0, 0, getWidth(), getHeight());//

                g2.setFont(new Font("Serif", Font.BOLD, 40));
                drawCenteredText(g2, title, A4_WIDTH / 2, 80);//Middle alignment

                g2.setFont(new Font("SansSerif", Font.BOLD, 18));
                int y = 140;//Starting y

                for (int i = 0; i < steps.length; i++) {
                    drawBoxedText(g2, "Step " + (i + 1) + ": " + steps[i], A4_WIDTH / 8, y, A4_WIDTH - 150, 80, palette[3]);
                    y += 100;//Vertical spacing
                }
            }

            private void drawCenteredText(Graphics2D g2, String text, int x, int y) {
                FontMetrics fm = g2.getFontMetrics();
                int width = fm.stringWidth(text);
                g2.setColor(palette[0]);
                g2.drawString(text, x - width / 2, y);
            }

            private void drawBoxedText(Graphics2D g2, String text, int x, int y, int width, int height, Color bgColor) {
                g2.setColor(bgColor);//Text
                //width -> A4_WIDTH - 150
                g2.fillRoundRect(x, y, width, height, 20, 20);//Fill color

                g2.setColor(palette[0]);
                g2.drawRoundRect(x, y, width, height, 20, 20);//Border

                // Draw multiline if text is too long
                g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
                FontMetrics fm = g2.getFontMetrics();
                int lineY = y + 25;

                String[] words = text.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    if (fm.stringWidth(line + word + " ") > width - 20) {
                        g2.drawString(line.toString(), x + 10, lineY);
                        line = new StringBuilder();
                        lineY += 20;
                    }
                    line.append(word).append(" ");
                }
                g2.drawString(line.toString(), x + 10, lineY);
            }
        };
    }
}
class DosDontsPosterFrame extends PosterFrame {
    private final String title;
    private final String[] dos;
    private final String[] donts;

    public DosDontsPosterFrame(String title, String[] dos, String[] donts, int colorChoice) {
        super("Dos and Don'ts Poster", colorChoice);
        this.title = title;
        this.dos = dos;
        this.donts = donts;
    }

    @Override
    protected JPanel createPosterPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(palette[5]);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setFont(new Font("Serif", Font.BOLD, 36));
                drawCenteredText(g2, title, A4_WIDTH / 2, 70);//This 7 lines of code are for initialization,which helps for setting the outline for any poster

                // Section Titles
                g2.setFont(new Font("SansSerif", Font.BOLD, 24));//24 size of Does and Donts
                g2.setColor(palette[0]);
                g2.drawString("DOs", A4_WIDTH / 5, 120);
                g2.drawString("DON'Ts", (A4_WIDTH * 3) / 5, 120);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 16));//16 is size of text written in it.
                int y = 160;
                for (int i = 0; i < dos.length; i++)//dos.length or donts.length 
                 {
                    drawBoxedText(g2, dos[i], A4_WIDTH / 10, y, A4_WIDTH / 2 - 60, 60, palette[2]);//A/10 is there because spacing is required between the boxes.
                    drawBoxedText(g2, donts[i], A4_WIDTH / 2 + 30, y, A4_WIDTH / 2 - 60, 60, palette[3]);
                    y += 100;//Vertical Spacing
                }
            }

            private void drawCenteredText(Graphics2D g2, String text, int x, int y) //Header 
            {
                FontMetrics fm = g2.getFontMetrics();
                int width = fm.stringWidth(text);
                g2.setColor(palette[0]);
                g2.drawString(text, x - width / 2, y);
            }

            private void drawBoxedText(Graphics2D g2, String text, int x, int y, int width, int height, Color bgColor) {
                g2.setColor(bgColor);
                g2.fillRoundRect(x, y, width, height, 20, 20);
                g2.setColor(palette[0]);
                g2.drawRoundRect(x, y, width, height, 20, 20);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
                FontMetrics fm = g2.getFontMetrics();
                int lineY = y + 25;

                String[] words = text.split(" ");
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    if (fm.stringWidth(line + word + " ") > width - 20) {
                        g2.drawString(line.toString(), x + 10, lineY);
                        line = new StringBuilder();
                        lineY += 18;
                    }
                    line.append(word).append(" ");
                }
                g2.drawString(line.toString(), x + 10, lineY);
            }
        };
    }
}
public class PosterGeneratorApp {
    public static void main(String[] args) {
        // Ensure GUI creation is on the EDT
        SwingUtilities.invokeLater (() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Choose Poster Type:");
            System.out.println("1. Informative Poster");
            System.out.println("2. Concept Map Poster");
            System.out.println("3. Dos and Don'ts Poster");
            System.out.println("4. Step-by-Step Poster");
            System.out.print("Enter your choice (1-4): ");
            int posterType = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("\nChoose a color palette (1-5):");
            System.out.println("1. Vibrant Rainbow");
            System.out.println("2. Ocean Breeze");
            System.out.println("3. Earth Tones");
            System.out.println("4. Pastel Dream");
            System.out.println("5. Bold Monochrome");
            System.out.print("Enter your choice (1-5): ");
            int colorChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (posterType) {
                case 1:
                    createInformativePoster(scanner, colorChoice);
                    break;
                case 2:
                    createConceptMapPoster(scanner, colorChoice);
                    break;
                case 3:
                    createDosAndDontsPoster(scanner, colorChoice);
                    break;
                case 4:
                    createStepByStepPoster(scanner, colorChoice);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            scanner.close();
        });
    }

    private static void createInformativePoster(Scanner scanner, int colorChoice) {
        System.out.println("\n=== Informative Poster ===");
        System.out.println("Enter Header (max 20 words):");
        String header = scanner.nextLine();

        String[] titles = new String[4];
        String[] descriptions = new String[4];

        for (int i = 0; i < 4; i++) {
            System.out.println("Enter title for Subpoint " + (i + 1) + " (max 15 words):");
            titles[i] = scanner.nextLine();
            System.out.println("Enter description for Subpoint " + (i + 1) + " (max 30 words):");
            descriptions[i] = scanner.nextLine();
        }

        new InformativePosterFrame(header, titles, descriptions, colorChoice);//IT extends PosterFrame class,so colorchoice will be passed.
    }
private static void createConceptMapPoster(Scanner scanner, int colorChoice) {
    System.out.println("\n=== Concept Map Poster ===");

    System.out.println("Enter Poster Header (press Enter to skip):");
    String header = scanner.nextLine();  // Optional

    System.out.println("Enter Central Idea:");
    String centerIdea = scanner.nextLine();

    String[] connections = new String[6];
    for (int i = 0; i < 6; i++) {
        System.out.println("Enter Connection " + (i + 1) + " (max 30 words):");
        connections[i] = scanner.nextLine();
    }

    new ConceptMapPosterFrame(header, centerIdea, connections, colorChoice);
}
   private static void createDosAndDontsPoster(Scanner scanner, int colorChoice) {
        System.out.println("\n=== Dos and Don'ts Poster ===");
        System.out.println("Enter Title (max 10 words):");
        String title = scanner.nextLine();

        String[] dos = new String[4];
        String[] donts = new String[4];

        for (int i = 0; i < 4; i++) {
            System.out.println("Enter Do " + (i + 1) + " (max 20 words):");
            dos[i] = scanner.nextLine();
        }

        for (int i = 0; i < 4; i++) {
            System.out.println("Enter Don't " + (i + 1) + " (max 20 words):");
            donts[i] = scanner.nextLine();
        }

        new DosDontsPosterFrame(title, dos, donts, colorChoice);
    }

private static void createStepByStepPoster(Scanner scanner, int colorChoice) {
    System.out.println("\n=== Step-by-Step Poster ===");
    System.out.println("Enter Process Title (max 10 words):");
    String title = scanner.nextLine();

    String[] steps = new String[5];
    for (int i = 0; i < 5; i++) {
        System.out.println("Enter Step " + (i + 1) + " (max 25 words):");
        steps[i] = scanner.nextLine();
    }

    new StepByStepPosterFrame(title, steps, colorChoice);
}

}
abstract class PosterFrame extends JFrame {
    protected Color[] palette;
    protected static final int A4_WIDTH = 794;
    protected static final int A4_HEIGHT = 1123;

    public PosterFrame(String title, int colorChoice) {
        super(title);
        setPalette(colorChoice);//setPalette method is called to set the color palette
        setupFrame();//setupFrame method is called to set the frame size and layout
    }

    private void setPalette(int colorChoice) {
        switch (colorChoice) {
            case 1: // Vibrant Rainbow
                palette = new Color[] {
                    new Color(255, 59, 48), new Color(255, 149, 0),
                    new Color(255, 204, 0), new Color(52, 199, 89),
                    new Color(0, 122, 255), Color.WHITE
                };
                break;
            case 2: // Ocean Breeze
                palette = new Color[] {
                    new Color(0, 130, 148), new Color(120, 220, 200),
                    new Color(100, 200, 255), new Color(0, 50, 100),
                    new Color(200, 240, 255), Color.WHITE
                };
                break;
            case 3: // Earth Tones
                palette = new Color[] {
                    new Color(139, 69, 19), new Color(210, 180, 140),
                    new Color(107, 142, 35), new Color(204, 85, 0),
                    new Color(255, 253, 208), Color.WHITE
                };
                break;
            case 4: // Pastel Dream
                palette = new Color[] {
                    new Color(255, 182, 193), new Color(220, 208, 255),
                    new Color(182, 255, 187), new Color(255, 215, 180),
                    new Color(180, 220, 255), Color.WHITE
                };
                break;
            case 5: // Bold Monochrome
            default:
                palette = new Color[] {
                    Color.BLACK, new Color(64, 64, 64),
                    new Color(128, 128, 128), new Color(192, 192, 192),
                    Color.LIGHT_GRAY, Color.WHITE
                };
                break;
        }
    }
  private void setupFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 700); // Initial window size
    setLocationRelativeTo(null); // Center the window on screen

    // Create the poster panel (this comes from your createPosterPanel() implementation)
    JPanel posterPanel = createPosterPanel();
    posterPanel.setPreferredSize(new Dimension(A4_WIDTH, A4_HEIGHT));

    // Create a wrapper panel that will center the poster
    JPanel wrapper = new JPanel(new GridBagLayout()) {
        @Override
        public Dimension getPreferredSize() {
            // Make wrapper at least as large as the viewport
            Dimension viewportSize = getParent() instanceof JViewport ? 
                getParent().getSize() : new Dimension(0, 0);
            Dimension posterSize = posterPanel.getPreferredSize();
            
            return new Dimension(
                Math.max(viewportSize.width, posterSize.width),
                Math.max(viewportSize.height, posterSize.height)
            );
        }
    };
    wrapper.setBackground(Color.LIGHT_GRAY); // Use the background color from your palette
    wrapper.add(posterPanel);

    // Configure the scroll pane
    JScrollPane scrollPane = new JScrollPane(wrapper);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
    
    // Configure scrollbar policies
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Add to frame
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    // Ensure proper display
    validate();
    setVisible(true);
}
    
        protected abstract JPanel createPosterPanel(); // Abstract method to be implemented by subclasses
}

