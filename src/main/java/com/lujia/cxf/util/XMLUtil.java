package com.lujia.cxf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLUtil {
    private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);
    private static final BASE64Decoder decoder = new BASE64Decoder();
    private static final BASE64Encoder encoder = new BASE64Encoder();


    private static Exception initEx;
    private static JAXBContext ctx;
    private static final Pattern succ = Pattern.compile("<success>([^>]+)</success>", Pattern.DOTALL);
    private static final Pattern msg = Pattern.compile("<msg>([^>]+)</msg>", Pattern.DOTALL);

    static {
        try {
            ctx = JAXBContext.newInstance(XMLUtil.class);
        } catch (JAXBException e) {
            initEx = e;
            e.printStackTrace();
        }
    }

    private static Marshaller getmMs() throws Exception {
        Marshaller ms = ctx.createMarshaller();
        ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ms.setProperty(Marshaller.JAXB_ENCODING, "gb18030");
        return ms;
    }

    @SuppressWarnings("unused")
    private static Marshaller getmMsf() throws Exception {
        Marshaller msf = ctx.createMarshaller();
        msf.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        msf.setProperty(Marshaller.JAXB_FRAGMENT, true);
        return msf;
    }

    private static Unmarshaller getUms() throws Exception {
        return ctx.createUnmarshaller();
    }

    public static String toXml(Object o) throws Exception {
        if (initEx != null) {
        }
        try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			getmMs().marshal(o, baos);
//			return baos.toString();
            StringWriter writer = new StringWriter();
            getmMs().marshal(o, writer);
            return writer.toString();

        } catch (Exception e) {
        }
        return null;
    }

    public static String toXmlFragment(Object o) throws Exception {
        if (initEx != null) {
            throw new Exception("Error init XMLUtil", initEx);
        }
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		getmMsf().marshal(o, baos);
//		return baos.toString();
        StringWriter writer = new StringWriter();
        getmMs().marshal(o, writer);
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml) throws Exception {
        if (initEx != null) {
        }
        try {
//			return (T) getUms().unmarshal(new ByteArrayInputStream(xml.getBytes()));
            StringReader reader = new StringReader(xml);
            return (T) getUms().unmarshal(reader);
        } catch (Exception e) {
        }
        return null;
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return gc;
    }

    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar cal){
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();

    }
    public static boolean success(String result) {
        boolean s = false;
        Matcher m = succ.matcher(result);
        if (m.find()) {
            return "0".equals(m.group(1).trim());
        }
        return s;
    }

    public static String msg(String result) {
        String s = null;
        Matcher m = msg.matcher(result);
        if (m.find()) {
            s = m.group(1).trim();
        }
        return s;
    }
}
