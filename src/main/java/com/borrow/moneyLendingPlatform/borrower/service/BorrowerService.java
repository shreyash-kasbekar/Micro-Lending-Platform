package com.borrow.moneyLendingPlatform.borrower.service;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanReqDTO;
import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.borrower.entity.Status;
import com.borrow.moneyLendingPlatform.borrower.repository.LoanRequestRepository;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import com.borrow.moneyLendingPlatform.lender.repository.BidRepository;
import com.borrow.moneyLendingPlatform.loan.service.LoanService;
import com.borrow.moneyLendingPlatform.user.entity.Users;
import com.borrow.moneyLendingPlatform.user.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRequestRepository loanRequestRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private LoanService loanService;


    @Transactional
    public LoanResponseDTO requestLoanCreation(String mail, LoanReqDTO loanReqDTO) {

        LoanRequest newloanRequest = mapToEntity(mail, loanReqDTO);
        LoanRequest saved = loanRequestRepository.save(newloanRequest);
        return mapToDTO(saved);

    }


    private LoanRequest mapToEntity(String mail, LoanReqDTO loanReqDTO) {

        LoanRequest loanRequest = new LoanRequest();
        Users borrower  = userRepository.findUserByEmail(mail).orElseThrow(()->new RuntimeException("Invalid User/Borrower Id in mapToEntity"));
        loanRequest.setBorrower(borrower);
        loanRequest.setAmount(loanReqDTO.getAmount());
        loanRequest.setTenureInMonths(loanReqDTO.getHoldingPeriodMonths());
        loanRequest.setPurpose(loanReqDTO.getPurpose());
        loanRequest.setStatusOfLoanReq(Status.OPEN);

        return loanRequest;
    }

    public LoanResponseDTO mapToDTO(LoanRequest newloanRequest) {
        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setLoanId(newloanRequest.getId());
        loanResponseDTO.setFromBorrower(newloanRequest.getBorrower().getUsername());
        loanResponseDTO.setAmount(newloanRequest.getAmount());
        loanResponseDTO.setHoldingPeriodMonths(newloanRequest.getTenureInMonths());
        loanResponseDTO.setPurpose(newloanRequest.getPurpose());
        loanResponseDTO.setStatus(newloanRequest.getStatusOfLoanReq().toString());

        return loanResponseDTO;
    }

    public LoanResponseDTO getLoanWithId(String mail, Long loanId) {
        return mapToDTO(loanRequestRepository.findById(loanId).orElseThrow(()->new RuntimeException("Incorrect Loan Id in getLoanWithId")));
    }

    public List<LoanResponseDTO> getMyAllLoanReq(String email) {

        List<LoanRequest> loanRequestList = loanRequestRepository.findByBorrower(userRepository.findUserByEmail(email).orElseThrow(()->new RuntimeException("Invalid Borrower/User Id in getMyAllLoanReq")));

        List<LoanResponseDTO> loanResponseDTOS = new ArrayList<>();


        for (LoanRequest loanReq: loanRequestList){
            loanResponseDTOS.add(mapToDTO(loanReq));
        }

        return loanResponseDTOS;
    }

    public List<Bid> allBidReqForLoanId(Long loanId) {
        return bidRepository.findByLoanRequest(loanRequestRepository.findById(loanId).orElseThrow(()->new RuntimeException("NO loan Request found for this id in allBidReqForLoanId")));
    }


    @Transactional
    public Bid acceptBid(String mail, Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(()->new RuntimeException("bid Not found with this id in acceptBid"));

        Users user = userRepository.findUserByEmail(mail).orElseThrow(() -> new RuntimeException("No user found in accept bid"));

        LoanRequest loanRequest = bid.getLoanRequest();

        //Checking if bid is accepted by the same person
        Long idFromLoanReq = loanRequest.getBorrower().getId();
        System.out.println("================== owner of loan req "+ idFromLoanReq);
        Long idFromUserWhoAcceptBid = user.getId();
        System.out.println("================== bid is accepted by "+ idFromUserWhoAcceptBid);

        if (!idFromLoanReq.equals(idFromUserWhoAcceptBid)){
            throw new RuntimeException("you are not authorized to accept the bid");
        }

        //Rejecting all other bids
        List<Bid> bidList = bidRepository.findByLoanRequest(loanRequest);
        for (Bid bid1: bidList){
            bid1.setStatus(com.borrow.moneyLendingPlatform.lender.entity.Status.REJECTED);
        }

        //accepting our bid
        bid.setStatus(com.borrow.moneyLendingPlatform.lender.entity.Status.ACCEPTED);
        loanService.bidAcceptedByBorrower(loanRequest, bid);
        return bid;
    }
}
