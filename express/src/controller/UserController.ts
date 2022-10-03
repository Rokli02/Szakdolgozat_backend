import {NextFunction, Request, Response} from "express";
import { User } from '../entity/User';
import { iUserService } from '../service/UserService';

export class UserController {
    private service: iUserService;
    constructor(service: iUserService) {
        this.service = service;
    }

    all = async (req: Request<{ page: number}, any, any, { size: number, filt: string, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
        const { page } = req.params;
        const { size, filt, ordr, dir } = req.query;
        
        try {
            const users = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
            return res.json({users: users[0], count: users[1]});
        } catch(err) {
            console.error('in all user:\n', err);
            next(err);
        }
    }

    one = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
        const { id } = req.params;

        try {
            const user = await this.service.findOne(id);
            return res.json({user});
        } catch(err) {
            console.error('in one user:\n', err);
            next(err);
        }
    }

    update = async (req: Request<{id: number}, any, User>, res: Response, next: NextFunction) => {
        const { id } = req.params;

        try {
            await this.service.update(id, req.body);
            return res.json({ message: 'Updated succesfully!' });
        } catch(err) {
            console.error('in update user:\n', err);
            next(err);
        }
    }

    remove = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
        const { id } = req.params;

        try {
            await this.service.remove(id);
            return res.json({ message: 'Deleted succesfully!', id });
        } catch(err) {
            console.error('in remove user:\n', err);
            next(err);
        }
    }
}