
export const validateNewUserFields = (req, res, next) => {
  let message: { message: string };
  message = validEmail(req.body.email);
  if(message) {
    return res.status(400).json(message)
  }
  
  message = validDate(req.body.birthdate);
  if(message) {
    return res.status(400).json(message)
  }

  next();
}

export const validateSeriesFields = (req, res, next) => {
  let message: { message: string };
  message = greaterOrEqualThanX(req.body.prodYear, 1900);
  if(req.body.prodYear && message) {
    return res.status(400).json(message);
  }

  message = greaterOrEqualThanX(req.body.ageLimit, 3);
  if(req.body.ageLimit && message) {
    return res.status(400).json(message);
  }

  message = greaterOrEqualThanX(req.body.length, 1);
  if(req.body.length && message) {
    return res.status(400).json(message);
  }

  if(req.body.seasons && req.body.seasons.length > 0) {
    for(let season of req.body.seasons) {
      message = greaterOrEqualThanX(season.season, 1);
      if(message) {
        return res.status(400).json(message);
      }

      message = greaterOrEqualThanX(season.episode, 1);
      if(message) {
        return res.status(400).json(message);
      }
    }
  }

  next();
}

export const validateUserFields = (req, res, next) => {
  let message: { message: string };
  message = validDate(req.body.birthdate);
  if(req.body.birthdate && message) {
    return res.status(400).json(message);
  }

  message = validEmail(req.body.email);
  if(req.body.email && message) {
    return res.status(400).json(message);
  }

  next();
}

export const validateUserSeriesFields = (req, res, next) => {
  let message: { message: string };
  message = greaterOrEqualThanX(req.body.season, 1);
  if(req.body.season && message) {
    return res.status(400).json(message);
  }

  message = greaterOrEqualThanX(req.body.episode, 1);
  if(req.body.episode && message) {
    return res.status(400).json(message);
  }

  next();
}

const validEmail = (email: string): { message: string } | undefined => {
  const emailRegex = /^[\w-\.]+@([\w-]{2,}\.)+[\w-]{2,4}$/;
  if(!emailRegex.test(email)) {
    return { message: "Email must be valid!" };
  }
  return undefined;
}

const validDate = (date: string) => {
  const dateRegex = /^((1[8-9][0-9]{2})|(20[0-9]{2}))[\\/.-]((0[1-9])|(1[0-2]))[\\/.-]((0[1-9])|([1-2][0-9])|(3[0-1]))$/;
  if(!dateRegex.test(date)) {
    return { message: "Birthdate must be valid!" };
  }
  return undefined;
}

const greaterOrEqualThanX = (num: number, x: number) => {
  if(num < x) {
    return { message: `Number must be greater than ${x}!` };
  }
  return undefined;
}