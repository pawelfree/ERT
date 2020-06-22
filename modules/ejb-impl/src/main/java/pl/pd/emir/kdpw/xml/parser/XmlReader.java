package pl.pd.emir.kdpw.xml.parser;

import javax.ejb.Stateless;
import pl.pd.emir.kdpw.xml.builder.XmlBuilder;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.modules.kdpw.adapter.model.RepositoryResponse;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionResponse;
import kdpw.xsd.IKDPWDocument;

@Stateless
public class XmlReader {

    public RepositoryResponse read(final String message) {

        RepositoryResponse result = null;

        try {
            final IKDPWDocument kdpwDocument = XmlBuilder.readMessage(message);

            if (kdpwDocument instanceof kdpw.xsd.trar_sts_001_02.KDPWDocument) {
                result = new RepositoryResponse(RepositoryResponse.ResponseType.TRANSACTION);

                kdpw.xsd.trar_sts_001_02.KDPWDocument document = (kdpw.xsd.trar_sts_001_02.KDPWDocument) kdpwDocument;
                for (kdpw.xsd.trar_sts_001_02.TrarSts00102 trar : document.getTrarSts00102()) {
                    TransactionResponse response = new TransactionResponse();
                    kdpw.xsd.trar_sts_001_02.Linkages link = trar.getGnlInf().getLnk();

                    // msgId
                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());

                     // ref msgId
                    if (null != link) {
                        if (null != link.getRltdRef()) {
                            response.setPrvsSndrMsgRef(link.getRltdRef().getPrvsSndrMsgRef());
                        }
                        response.setRltdReqRef(link.getRltdReqRef());
                    }

                    // status
                    if (null != trar.getSts()) {
                        final kdpw.xsd.trar_sts_001_02.Status status = trar.getSts();
                        response.setStatusCode(status.getStsCd());
                        if (status.getRsn() != null) {
                            response.setReasonCode(status.getRsn().getRsnCd());
                            response.setReasonText(status.getRsn().getRsnTxt());
                        }
}
                    result.addToList(response);
                }         
            }
            else 
            // odpowiedz na komunikat z danymi TRANSAKCJI
            if (kdpwDocument instanceof kdpw.xsd.trar_sts_001.KDPWDocument) {
                result = new RepositoryResponse(RepositoryResponse.ResponseType.TRANSACTION);
                kdpw.xsd.trar_sts_001.KDPWDocument document = (kdpw.xsd.trar_sts_001.KDPWDocument) kdpwDocument;
                for (kdpw.xsd.trar_sts_001.TrarSts00104 trar : document.getTrarSts00104()) {
                    TransactionResponse response = new TransactionResponse();
                    kdpw.xsd.trar_sts_001.Linkages link = trar.getGnlInf().getLnk();

                    // msgId
                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());

                    if (null != link) {
                        response.setPrvsSndrMsgRef(link.getPrvsSndrMsgRef());
                    }

                    // status
                    if (null != trar.getSts()) {
                        final kdpw.xsd.trar_sts_001.Status status = trar.getSts();
                        response.setStatusCode(status.getStsCd());
                        if (status.getRsn() != null) {
                            response.setReasonCode(status.getRsn().getRsnCd());
                            response.setReasonText(status.getRsn().getRsnTxt());
                        }
                    }
                    result.addToList(response);
                }
            } else {
                throw new IllegalArgumentException("Invalid message document type !");
            }
        } catch (XmlParseException ex) {
            result = new RepositoryResponse(RepositoryResponse.ResponseType.INVALID);
            /// TODO LOGGER
        }
        return result;
    }
}
