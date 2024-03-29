import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn, Unique } from 'typeorm';
import { Series } from './Series';

@Entity()
@Unique(['season', 'series'])
export class Season {
  @PrimaryGeneratedColumn()
    id: number;

    @Column({ nullable: false })
    season: number;

    @Column({ nullable: false })
    episode: number;

    @ManyToOne(() => Series, series => series.seasons, {
      nullable: false,
      onDelete: 'CASCADE'
    })
    @JoinColumn({ name: "f_series_id" })
    series: Series;
}