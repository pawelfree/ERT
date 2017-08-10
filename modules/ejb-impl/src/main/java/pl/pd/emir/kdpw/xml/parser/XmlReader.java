package pl.pd.emir.kdpw.xml.parser;

import java.util.Date;
import javax.ejb.Stateless;
import pl.pd.emir.commons.Constants;
import pl.pd.emir.kdpw.xml.builder.XmlBuilder;
import pl.pd.emir.kdpw.xml.builder.XmlParseException;
import pl.pd.emir.kdpw.xml.builder.XmlUtils;
import pl.pd.emir.modules.kdpw.adapter.model.RepositoryResponse;
import pl.pd.emir.modules.kdpw.adapter.model.ResponseItem;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionLink;
import pl.pd.emir.modules.kdpw.adapter.model.TransactionResponse;
import kdpw.xsd.IKDPWDocument;
import kdpw.xsd.trar_sts_001.DateAndDateTimeChoice;

@Stateless
public class XmlReader {

    public RepositoryResponse read(final String message) {

        RepositoryResponse result = null;
//TODO - comment
//        try {
//            final IKDPWDocument kdpwDocument = XmlBuilder.readMessage(message);
//
//            // odpowiedz na komunikat z danymi TRANSAKCJI
//            if (kdpwDocument instanceof kdpw.xsd.trar_sts_001.KDPWDocument) {
//                result = new RepositoryResponse(RepositoryResponse.ResponseType.TRANSACTION);
//
//                kdpw.xsd.trar_sts_001.KDPWDocument document = (kdpw.xsd.trar_sts_001.KDPWDocument) kdpwDocument;
//                for (kdpw.xsd.trar_sts_001.TrarSts00103 trar : document.getTrarSts00103()) {
//                    TransactionResponse response = new TransactionResponse();
//                    kdpw.xsd.trar_sts_001.Linkages link = trar.getGnlInf().getLnk();
//
//                    // msgId
//                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());
//
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
//
//                    // ref msgId
//                    if (null != link) {
//                        if (null != link.getRltdRef()) {
//                            response.setPrvsSndrMsgRef(link.getRltdRef().getPrvsSndrMsgRef());
//                        }
//                        response.setRltdReqRef(link.getRltdReqRef());
//                    }
//
//                    // status
//                    if (null != trar.getSts()) {
//                        final kdpw.xsd.trar_sts_001.Status status = trar.getSts();
//                        response.setStatusCode(status.getStsCd());
//                        if (status.getRsn() != null) {
//                            response.setReasonCode(status.getRsn().getRsnCd());
//                            response.setReasonText(status.getRsn().getRsnTxt());
//                        }
//                    }
//
//                    // TR_C%
//                    if (trar.getGnlInf().getSndrMsgRef().startsWith(Constants.TR_C)) {
//                        if (null != link) {
//                            link.getTradRefId().stream()
//                                    .map((tradeReference) -> {
//                                        TransactionLink transLink = new TransactionLink();
//                                        transLink.setTradeIdId(tradeReference.getTradId());
//                                        transLink.setCtrPtyTRId(tradeReference.getCtrPtyTRId());
//                                        transLink.setEligDate(XmlUtils.getDate(trar.getGnlInf().getEligDt()));
//                                        return transLink;
//                                    })
//                                    .forEach(transLink -> response.addLink(transLink));
//                        }
//                    }
//                    result.addToList(response);
//                }
//
//            } else // odpoowiedz na komunikat z danymi ZBIORCZYMI
//            if (kdpwDocument instanceof kdpw.xsd.trar_sts_002.KDPWDocument) {
//                result = new RepositoryResponse(RepositoryResponse.ResponseType.DATASET);
//                final kdpw.xsd.trar_sts_002.KDPWDocument document = (kdpw.xsd.trar_sts_002.KDPWDocument) kdpwDocument;
//
//                for (kdpw.xsd.trar_sts_002.TrarSts00202 trar : document.getTrarSts00202()) {
//                    ResponseItem response = new ResponseItem();
//                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());
//                    final kdpw.xsd.trar_sts_002.Linkages link = trar.getGnlInf().getLnk();
//                    if (link != null && link.getRltdRef() != null) {
//                        response.setPrvsSndrMsgRef(link.getRltdRef().getPrvsSndrMsgRef());
//                    }
//                    if (null != trar.getSts()) {
//                        final kdpw.xsd.trar_sts_002.Status status = trar.getSts();
//                        response.setStatusCode(status.getStsCd());
//                        if (status.getRsn() != null) {
//                            response.setReasonCode(status.getRsn().getRsnCd());
//                            response.setReasonText(status.getRsn().getRsnTxt());
//                        }
//                    }
//                    result.addToList(response);
//                }
//            } else if (kdpwDocument instanceof kdpw.xsd.trar_rcn_001.KDPWDocument) {
//                result = new RepositoryResponse(RepositoryResponse.ResponseType.RECONCILIATION);
//                kdpw.xsd.trar_rcn_001.KDPWDocument document = (kdpw.xsd.trar_rcn_001.KDPWDocument) kdpwDocument;
//                for (kdpw.xsd.trar_rcn_001.TrarRcn00102 trar : document.getTrarRcn00102()) {
//                    ResponseItem response = new ResponseItem();
//                    response.setSndrMsgRef(trar.getGnlInf().getSndrMsgRef());
//                    if (null != trar.getSts()) {
//                        response.setStatusCode(trar.getSts().getStsCd());
//                    }
//                }
//            } else {
//                throw new IllegalArgumentException("Invalid message document type !");
//            }
//        } catch (XmlParseException ex) {
//            result = new RepositoryResponse(RepositoryResponse.ResponseType.INVALID);
//            /// TODO LOGGER
//        }
        return result;
    }
}
