package rock.data;

import rock.exception.RockException;

public class MemberProxy implements Proxy {

    private Rock rock;
    private String member;

    public MemberProxy(Rock rock, String member) {
        this.rock = rock;
        this.member = member;
    }

    @Override
    public Rock get() throws RockException {
        return rock.getMember(member);
    }

    @Override
    public Rock set(Rock val) throws RockException {
        return rock.setMember(member, val);
    }
}
