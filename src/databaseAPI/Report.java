package databaseAPI;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Report {

	private static JasperReport jreport;
	private static JasperViewer jviewer;
	private static JasperPrint jprint;

	public static void creatReport(Connection connect, Map<String, Object> map, InputStream by) throws JRException {

		jreport = (JasperReport) JRLoader.loadObject(by);
		jprint = JasperFillManager.fillReport(jreport, map, connect);

	}

	public static void showReport() {

		jviewer = new JasperViewer(jprint);
		jviewer.setVisible(true);

	}

}
