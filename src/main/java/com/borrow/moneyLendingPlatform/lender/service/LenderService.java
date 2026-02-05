package com.borrow.moneyLendingPlatform.lender.service;

import com.borrow.moneyLendingPlatform.borrower.dto.LoanResponseDTO;
import com.borrow.moneyLendingPlatform.borrower.entity.LoanRequest;
import com.borrow.moneyLendingPlatform.borrower.repository.LoanRequestRepository;
import com.borrow.moneyLendingPlatform.borrower.service.BorrowerService;
import com.borrow.moneyLendingPlatform.lender.dto.BidReqDTO;
import com.borrow.moneyLendingPlatform.lender.dto.BidResponseDTO;
import com.borrow.moneyLendingPlatform.lender.entity.Bid;
import com.borrow.moneyLendingPlatform.lender.entity.Status;
import com.borrow.moneyLendingPlatform.lender.repository.BidRepository;
import com.borrow.moneyLendingPlatform.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LenderService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    public List<LoanResponseDTO> getAllOpenLoanReq() {
        List<LoanRequest> loanRequestList = loanRequestRepository.findAll();

        List<LoanResponseDTO> loanResponseDTOS = new ArrayList<>();

        for (LoanRequest loanRequest: loanRequestList){
            loanResponseDTOS.add(borrowerService.mapToDTO(loanRequest));
        }
        return loanResponseDTOS;
    }


    public BidResponseDTO placeBidForLoanReq(BidReqDTO bidReqDTO) {
        Bid newBid = mapToEntity(bidReqDTO);
        Bid savedBid= bidRepository.save(newBid);
        return mapToDTO(savedBid);

    }

    private BidResponseDTO mapToDTO(Bid savedBid) {
        BidResponseDTO bidResponseDTO = new BidResponseDTO();
        bidResponseDTO.setBidId(savedBid.getId());
        bidResponseDTO.setLoanReqId(savedBid.getLoanRequest().getId());
        bidResponseDTO.setLenderId(savedBid.getLender().getId());
        bidResponseDTO.setOfferedAmount(savedBid.getOfferedAmount());
        bidResponseDTO.setInterestRate(savedBid.getInterestRate());
        bidResponseDTO.setMessage(savedBid.getMessage());
        bidResponseDTO.setStatus(savedBid.getStatus().toString());
        return bidResponseDTO;
    }

    private Bid mapToEntity(BidReqDTO bidReqDTO) {
        Bid bid = new Bid();
        bid.setLoanRequest(loanRequestRepository.findById(bidReqDTO.getLoanId()).orElseThrow(() -> new RuntimeException("Incorrect Loan Request ID in mapToEntity(Lender Module)")));
        bid.setLender(userRepository.findById(bidReqDTO.getFromUserId()).orElseThrow(()-> new RuntimeException("No User Found for this id to set lender")));
        bid.setOfferedAmount(bidReqDTO.getOfferedAmount());
        bid.setInterestRate(bidReqDTO.getInterestRate());
        bid.setMessage(bidReqDTO.getMessage());
        bid.setStatus(Status.PENDING);
        return bid;
    }

    public List<BidResponseDTO> getMyAllBids(Long lenderId) {
        List<Bid> bidList = bidRepository.findByLender(userRepository.findById(lenderId).orElseThrow(()->new RuntimeException("No Lender found for this id in getMyAllBids")));
        List<BidResponseDTO> bidResponseDTOS = new ArrayList<>();
        for (Bid bid: bidList){
            bidResponseDTOS.add(mapToDTO(bid));
        }
        return bidResponseDTOS;
    }
}
