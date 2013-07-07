package com.mreapps.zapezy.service.service.impl;

import com.mreapps.zapezy.service.service.EpgService;
import com.mreapps.zapezy.service.service.ProgrammeImportService;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Programme;
import com.mreapps.zapezy.service.xmlbeans.xmltv.Tv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

@Service
@Transactional(readOnly = true)
public class EpgServiceImpl implements EpgService
{
    @Autowired
    private ProgrammeImportService programmeImportService;

    @Override
    @Transactional(readOnly = false)
    public int refreshEpg() throws IOException, JAXBException
    {
        String[] urls = {
                "http://www.xmltvepg.be/rytecxmltvnordic.gz",
                "http://www.world-of-satellite.com/epg_data/rytecxmltvnordic.gz",
                "http://www.xmltvepg.nl/rytecxmltvnordic.gz",
                "http://www.tm800hd.co.uk/rytec/rytecxmltvnordic.gz",
                "http://rytec.tman.nl/rytecxmltvnordic.gz"
        };

        final Counter counter = new Counter();
        for (String urlAsString : urls)
        {
            try
            {
                downloadAndUnmarshal(urlAsString, new ParsingCallback<Programme>()
                {
                    @Override
                    public void processEntitites(Collection<Programme> programmes)
                    {
                        counter.count += programmeImportService.storeProgrammes(programmes);
                    }
                }, 250);
            } catch (IOException e)
            {
                // try next url
            }
        }
        return counter.count;
    }

    private <T> void downloadAndUnmarshal(String urlAsString, final ParsingCallback<T> parsingCallback, final int bufferSize) throws IOException, JAXBException
    {
        URL url = new URL(urlAsString);
        InputStream inputStream = null;
        try
        {
            inputStream = new GZIPInputStream(url.openStream());

            JAXBContext context = JAXBContext.newInstance("com.mreapps.zapezy.service.xmlbeans.xmltv");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            final Collection<T> buffer = new ArrayList<T>();
            @SuppressWarnings("unchecked") final Class<T> entityClass = (Class<T>) ((ParameterizedType) parsingCallback.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            unmarshaller.setListener(new Unmarshaller.Listener()
            {
                @Override
                public void afterUnmarshal(Object target, Object parent)
                {
                    if (entityClass.isInstance(target))
                    {
                        //noinspection unchecked
                        buffer.add((T) target);
                        if (buffer.size() >= bufferSize)
                        {
                            parsingCallback.processEntitites(buffer);
                            buffer.clear();
                            if (parent instanceof Tv)
                            {
                                Tv tv = (Tv) parent;
                                tv.getProgramme().clear();
                            }
                        }
                    }

                    if (target instanceof Tv && !buffer.isEmpty())
                    {
                        parsingCallback.processEntitites(buffer);
                    }
                }
            });

            // create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XMLReader reader = factory.newSAXParser().getXMLReader();
            reader.setContentHandler(unmarshaller.getUnmarshallerHandler());

            reader.parse(new InputSource(inputStream));
        } catch (SAXException e)
        {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    private interface ParsingCallback<T>
    {
        void processEntitites(Collection<T> ts);
    }

    private class Counter
    {
        int count;
    }
}
