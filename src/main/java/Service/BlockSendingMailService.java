package Service;

import Dao.BlockSendingMailDao;
import Entity.BlockSendingMail;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class BlockSendingMailService {

    private BlockSendingMailDao blockSendingMailDao = new BlockSendingMailDao();

    public BlockSendingMailService() {}

    public List<BlockSendingMail> getAllBlockSendingMails(){
        return blockSendingMailDao.getAllBlockSendingMails();
    }

    public void deleteBlockSendingMail(BlockSendingMail blockSendingMail) throws UnsupportedEncodingException {
        blockSendingMailDao.deleteBlockSendingMail(blockSendingMail);
    }

    public void updateBlockSendingMail(BlockSendingMail blockSendingMail) throws UnsupportedEncodingException {
        blockSendingMailDao.updateBlockSendingMail(blockSendingMail);
    }

    public void addBlockSendingMail(BlockSendingMail blockSendingMail) throws UnsupportedEncodingException {
        blockSendingMailDao.addBlockSendingMail(blockSendingMail);
    }

}
