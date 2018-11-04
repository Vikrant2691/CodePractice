package com.test.adwords;

import static com.google.api.ads.common.lib.utils.Builder.DEFAULT_CONFIGURATION_FILENAME;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.reporting.ReportingConfiguration;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.jaxb.v201802.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201802.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201802.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.utils.DetailedReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201802.ReportDownloaderInterface;
import com.google.api.ads.adwords.lib.utils.v201802.ReportQuery;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import adwords.axis.v201802.reportTesting.Report;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * This example downloads a criteria performance report with AWQL.
 *
 * <p>
 * Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 */
public class DownloadCriteriaReportWithAwql {

	public static void main(String[] args) throws JAXBException {
		AdWordsSession session;
		try {
			// Generate a refreshable OAuth2 credential.
			Credential oAuth2Credential = new OfflineCredentials.Builder().forApi(Api.ADWORDS).fromFile().build()
					.generateCredential();

			// Construct an AdWordsSession.
			session = new AdWordsSession.Builder().fromFile().withOAuth2Credential(oAuth2Credential).build();
		} catch (ConfigurationLoadException cle) {
			System.err.printf("Failed to load configuration from the %s file. Exception: %s%n",
					DEFAULT_CONFIGURATION_FILENAME, cle);
			return;
		} catch (ValidationException ve) {
			System.err.printf("Invalid configuration in the %s file. Exception: %s%n", DEFAULT_CONFIGURATION_FILENAME,
					ve);
			return;
		} catch (OAuthException oe) {
			System.err.printf(
					"Failed to create OAuth credentials. Check OAuth settings in the %s file. " + "Exception: %s%n",
					DEFAULT_CONFIGURATION_FILENAME, oe);
			return;
		}

		AdWordsServicesInterface adWordsServices = AdWordsServices.getInstance();

		// Location to download report to.
		String reportFile = System.getProperty("user.home") + File.separatorChar + "report.csv";

		try {
			runExample(adWordsServices, session, reportFile);
		} catch (DetailedReportDownloadResponseException dre) {
			// A DetailedReportDownloadResponseException will be thrown if the HTTP status
			// code in the
			// response indicates an error occurred and the response body contains XML with
			// further
			// information, such as the fieldPath and trigger.
			System.err.printf(
					"Report was not downloaded due to a %s with errorText '%s', trigger '%s' and "
							+ "field path '%s'%n",
					dre.getClass().getSimpleName(), dre.getErrorText(), dre.getTrigger(), dre.getFieldPath());
		} catch (ReportDownloadResponseException rde) {
			// A ReportDownloadResponseException will be thrown if the HTTP status code in
			// the response
			// indicates an error occurred, but the response did not contain further
			// details.
			System.err.printf("Report was not downloaded due to: %s%n", rde);
		} catch (ReportException re) {
			// A ReportException will be thrown if the download failed due to a transport
			// layer exception.
			System.err.printf("Report was not downloaded due to transport layer exception: %s%n", re);
		} catch (IOException ioe) {
			// An IOException in this example indicates that the report's contents could not
			// be written
			// to the output file.
			System.err.printf("Report was not written to file %s due to an IOException: %s%n", reportFile, ioe);
		}
	}

	/**
	 * Runs the example.
	 *
	 * @param adWordsServices
	 *            the services factory.
	 * @param session
	 *            the session.
	 * @param reportFile
	 *            the output file for the report contents.
	 * @throws DetailedReportDownloadResponseException
	 *             if the report request failed with a detailed error from the
	 *             reporting service.
	 * @throws ReportDownloadResponseException
	 *             if the report request failed with a general error from the
	 *             reporting service.
	 * @throws ReportException
	 *             if the report request failed due to a transport layer error.
	 * @throws IOException
	 *             if the report's contents could not be written to
	 *             {@code reportFile}.
	 * @throws JAXBException
	 */
	public static void runExample(AdWordsServicesInterface adWordsServices, AdWordsSession session, String reportFile)
			throws ReportDownloadResponseException, ReportException, IOException, JAXBException {
		// Create query.
		ReportQuery query = new ReportQuery.Builder()
				.fields("AccountCurrencyCode", "AccountDescriptiveName", "AccountTimeZone", "CanManageClients",
						"CustomerDescriptiveName", "ExternalCustomerId", "IsAutoTaggingEnabled")
				.from(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
				.during(ReportDefinitionDateRangeType.LAST_7_DAYS).build();

		// Optional: Set the reporting configuration of the session to suppress header,
		// column name, or
		// summary rows in the report output. You can also configure this via your
		// ads.properties
		// configuration file. See AdWordsSession.Builder.from(Configuration) for
		// details.
		// In addition, you can set whether you want to explicitly include or exclude
		// zero impression
		// rows.
		ReportingConfiguration reportingConfiguration = new ReportingConfiguration.Builder().skipReportHeader(false)
				.skipColumnHeader(false).skipReportSummary(false)
				// Set to false to exclude rows with zero impressions.
				.includeZeroImpressions(true).build();
		session.setReportingConfiguration(reportingConfiguration);

		ReportDownloaderInterface reportDownloader = adWordsServices.getUtility(session,
				ReportDownloaderInterface.class);

		// Set the property api.adwords.reportDownloadTimeout or call
		// ReportDownloader.setReportDownloadTimeout to set a timeout (in milliseconds)
		// for CONNECT and READ in report downloads.
		ReportDownloadResponse response = reportDownloader.downloadReport(query.toString(), DownloadFormat.XML);

		// response.saveToFile(reportFile);
		String op = response.getAsString();
		System.out.println(op.replace("-", ""));
		JAXBContext jc = JAXBContext.newInstance(Report.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource streamSource = new StreamSource(new StringReader(op.replace("-", "")));
		JAXBElement<Report> je = unmarshaller.unmarshal(streamSource, Report.class);
		Report s = (Report) je.getValue();
		System.out.println(s.toString());
		org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
		Configuration c = HBaseConfiguration.create(); // Instantiate a configuration class'
		c.set("hbase.zookeeper.quorum", "172.24.0.22");
		c.set("hbase.zookeeper.property.clientPort", "2181");
		c.set("zookeeper.znode.parent", "/hbase");
		c.set("hbase.nameserver.address","172.24.0.22" );
	    c.set("hbase.master","172.24.0.22:60000" );
	    Connection connection = ConnectionFactory.createConnection(c);
	    Table table = connection.getTable(TableName.valueOf("emp"));
	    try {
	    	Put p = new Put(Bytes.toBytes("test2"));
			p.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("test"),Bytes.toBytes("456"));
			table.put(p);
			table.close();
			
	    }
	    catch(Exception e) {
	    	System.out.println("Error: "+e);
	    }
	    finally {
	      table.close();
	      connection.close();
	    }
		

		// System.out.printf("Report successfully downloaded to: %s%n", reportFile);
	}
}
