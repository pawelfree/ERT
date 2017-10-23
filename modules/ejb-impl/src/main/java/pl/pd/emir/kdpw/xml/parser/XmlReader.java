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

            // odpowiedz na komunikat z danymi TRANSAKCJI
            if (kdpwDocument instanceof kdpw.xsd.trar_sts_001.KDPWDocument) {
                result = new RepositoryResponse(RepositoryResponse.ResponseType.TRANSACTION);
                kdpw.xsd.trar_sts_001.KDPWDocument document = (kdpw.xsd.trar_sts_001.KDPWDocument) kdpwDocument;
                for (kdpw.xsd.trar_sts_001.TrarSts00103 trar : document.getTrarSts00103()) {
                    TransactionResponse response = new TransactionResponse();
                    kdpw.xsd.trar_sts_001.Linkages link = trar.getGnlInf().getLnk();

                    // msgId
                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());

//TODO to jest terminacja ktorej nie obslugujemy                    
//                    if (trar.getTradDtls() != null && trar.getTradDtls().getTradAddtlInf() != null
//                            && trar.getTradDtls().getTradAddtlInf().getTrmntnDt() != null) {
//                        DateAndDateTimeChoice trmntnDt = trar.getTradDtls().getTradAddtlInf().getTrmntnDt();
//                        Date trmDate = null;
//                        if (trmntnDt.getDt() != null) {
//                            trmDate = XmlUtils.getDate(trar.getTradDtls().getTradAddtlInf().getTrmntnDt().getDt());
//                        } else if (trmntnDt.getDtTm() != null) {
//                            trmDate = XmlUtils.getDate(trar.getTradDtls().getTradAddtlInf().getTrmntnDt().getDtTm());
//                        }
//                        response.setTerminationDate(trmDate);
//                    }

                    // ref msgId
                    //TODO sprawdzić po co było
//                        if (null != link.getPrvsSndrMsgRef()) {
//                            response.setPrvsSndrMsgRef(link.getPrvsSndrMsgRef());
//                        }
//                        response.setRltdReqRef(link.getRltdReqRef());
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
