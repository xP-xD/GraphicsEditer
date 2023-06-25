package menuItems;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import frames.GDrawingPanel;

public class GPrinting implements Printable{

	private GDrawingPanel gDrawingPanel;
	
	public GPrinting(GDrawingPanel gDrawingPanel) {
		this.gDrawingPanel = gDrawingPanel;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        gDrawingPanel.printAll(graphics); // GDrawingPanel의 그림을 프린터로 출력

        return PAGE_EXISTS;
	}
	
	public void printDrawing() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		
		if(job.printDialog()) {
			try {
				job.print();
			}catch(PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	
}
