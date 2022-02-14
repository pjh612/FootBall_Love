import produce from 'immer';

type User = {
  createdDate: string;
  lastModifiedDate: string;
  createdBy: null | number;
  lastModifiedBy: null | number;
  number: null | number;
  id: string;
  nickname: string;
  name: string;
  email: string;
  birth: string;
  address?: {
    city: string;
    street: string;
    zipcode: string;
  };
  phone: string;
  profileUri: null | string;
  type: string;
  company: null | string;
  teams?: any;
};

const initialUser: { user: User } = {
  user: {
    createdDate: '',
    lastModifiedDate: '',
    createdBy: null,
    lastModifiedBy: null,
    number: null,
    id: '',
    nickname: '',
    name: '',
    email: '',
    birth: '',
    address: {
      city: '',
      street: '',
      zipcode: '',
    },
    phone: '',
    profileUri: null,
    type: '',
    company: null,
    teams: [],
  },
};

function userReducer(state, action) {
  if (state === undefined) {
    return initialUser;
  }

  switch (action.type) {
    case 'UPDATE_USERINFO':
      return {
        ...state,
        user: { ...action.update },
      };

    case 'UPDATE_PROFILE_PHOTO_URI':
      return produce(state, (draft) => {
        const newUri = action.uri;
        draft.user.profileUri = newUri;
      });

    case 'UPDATE_TEAMINFO':
      return produce(state, (draft) => {
        const newTeamInfo = action.teaminfo;
        draft.user.teams = newTeamInfo;
      });

    case 'LOGOUT':
      return produce(state, (draft) => {
        draft.user = null;
      });

    default:
      return { ...state };
  }
}

export { userReducer };
